#!/usr/bin/python
import os
from subprocess import Popen, PIPE

class CCompiler(object):

    def compile(self, source_path, extra_args = [], exec_path = "./a.out"):
        '''
        Returns info whether compilation was successful and path to executable
        '''
        
        if source_path is None or exec_path is None: raise TypeError
        if exec_path == '': raise ValueError

        if not os.path.exists(source_path):
            raise IOError("File " + source_path + " does not exist")

        # Must be compiled statically for sake of safety reasons (-static)
        p = Popen(['gcc', source_path, '-o', exec_path, '-static'] + extra_args, 
            stdin = PIPE, stdout = PIPE, stderr = PIPE)

        return_code = p.wait()

        if return_code != 0 and os.path.exists(exec_path): 
            os.remove(self.exec_path)

        return (return_code == 0, exec_path)
