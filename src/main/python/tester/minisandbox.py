################################################################################
# The Sandbox Libraries (Python) - Sample Script                               #
#                                                                              #
# Copyright (C) 2009-2013 LIU Yu, <pineapple.liu@gmail.com>                    #
# All rights reserved.                                                         #
#                                                                              #
# Redistribution and use in source and binary forms, with or without           #
# modification, are permitted provided that the following conditions are met:  #
#                                                                              #
# 1. Redistributions of source code must retain the above copyright notice,    #
#    this list of conditions and the following disclaimer.                     #
#                                                                              #
# 2. Redistributions in binary form must reproduce the above copyright notice, #
#    this list of conditions and the following disclaimer in the documentation #
#    and/or other materials provided with the distribution.                    #
#                                                                              #
# 3. Neither the name of the author(s) nor the names of its contributors may   #
#    be used to endorse or promote products derived from this software without #
#    specific prior written permission.                                        #
#                                                                              #
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"  #
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE    #
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE   #
# ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE     #
# LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR          #
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF         #
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS     #
# INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN      #
# CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)      #
# ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE   #
# POSSIBILITY OF SUCH DAMAGE.                                                  #
################################################################################

import os
import sys

try:
    # check platform type
    system, machine = os.uname()[0], os.uname()[4]
    if system not in ('Linux', ) or machine not in ('i686', 'x86_64', ):
        raise AssertionError("Unsupported platform type.\n")
    # check package availability / version
    import sandbox
    if not hasattr(sandbox, '__version__') or sandbox.__version__ < "0.3.4-3":
        raise AssertionError("Unsupported sandbox version.\n")
    from sandbox import *
except ImportError:
    sys.stderr.write("Required package(s) missing.\n")
    sys.exit(os.EX_UNAVAILABLE)
except AssertionError as e:
    sys.stderr.write(str(e))
    sys.exit(os.EX_UNAVAILABLE)

# possible result codes 
# (ORIGINAL_CODE = No, < Description /= SIMPLIFIED_CODE/)
# PD = 0, < Pending
# OK = 1, < Okay
# RF = 2, < Restricted Function = SYS
# ML = 3, < Memory Limit Exceed = RTE
# OL = 4, < Output Limit Exceed = RTE
# TL = 5, < Time Limit Exceed = TLE
# RT = 6, < Run Time Error (SIGSEGV, SIGFPE, ...) = RTE
# AT = 7, < Abnormal Termination = RTE
# IE = 8, < Internal Error (of sandbox executor) = IE
# BP = 9, < Bad Policy (since 0.3.3) = IE

def result_name(r):
    return ('PD', 'OK', 'SYS', 'RTE', 'RTE', 'TLE', 'RTE', 'RTE', 'IE', 'IE')[r] \
        if r in range(10) else None

# mini sandbox with embedded policy
class MiniSandbox(SandboxPolicy,Sandbox):
    sc_table = None
    # white list of essential linux syscalls for statically-linked C programs
    sc_safe = dict(i686 = set([0, 3, 4, 19, 45, 54, 90, 91, 122, 125, 140, \
        163, 192, 197, 224, 243, 252, ]), x86_64 = set([0, 1, 5, 8, 9, 10, \
        11, 12, 16, 25, 63, 158, 219, 231, ]), )

    def __init__(self, *args, **kwds):
        # initialize table of system call rules
        self.sc_table = [self._KILL_RF, ] * 1024
        for scno in MiniSandbox.sc_safe[machine]:
            self.sc_table[scno] = self._CONT
        # initialize as a polymorphic sandbox-and-policy object
        SandboxPolicy.__init__(self)
        Sandbox.__init__(self, *args, **kwds)
        self.policy = self

    def probe(self):
        # add custom entries into the probe dict
        d = Sandbox.probe(self, False)
        d['cpu'] = d['cpu_info'][0]
        d['mem'] = d['mem_info'][1]
        d['result'] = result_name(self.result)
        return d

    def __call__(self, e, a):
        # handle SYSCALL/SYSRET events with local rules
        if e.type in (S_EVENT_SYSCALL, S_EVENT_SYSRET):
            if machine == 'x86_64' and e.ext0 != 0:
                return self._KILL_RF(e, a)
            return self.sc_table[e.data](e, a)
        # bypass other events to base class
        return SandboxPolicy.__call__(self, e, a)

    def _CONT(self, e, a): # continue
        a.type = S_ACTION_CONT
        return a

    def _KILL_RF(self, e, a): # restricted func.
        a.type, a.data = S_ACTION_KILL, S_RESULT_RF
        return a

