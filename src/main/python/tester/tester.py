#!/usr/bin/python
from compiler import CCompiler
from minisandbox import MiniSandbox

class Tester(object):
    def __init__(self, compiler = CCompiler(), sandbox = None, validators = []):
        self._compiler = compiler
        self._sandbox = sandbox
        self._validators = validators

    def test(self, submit):
        compiled, exec_path = self._compiler.compile(submit['source_path'])

        if not compiled: return self._make_cme_response()

    #TODO:
    def _make_cme_response(self):
        pass;

        
    
        
        
