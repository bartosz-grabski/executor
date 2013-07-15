#!/usr/bin/python
import unittest
import os
from unittest.mock import MagicMock

# TODO:
class TestTester(unittest.TestCase):  

    def setUp(self):
        self._compiler = CCompiler()
        self._sandbox = MiniSandbox()
        self._validator1 = Validator()
        self._validator2 = Validator()

        self.tester = Tester(compiler = self._compiler, sandbox = self._sandbox, validators = [self._validator1, self._validator2 ])

        self.SUBMIT = { 'id': 123, 'source_path': './main.c', \
            'tests' : [{'id': 1, 'input': '1', 'output' : '1', 'memory_max_bytes': 1024 ** 2,'time_max_ms': 1000 }] }

        self.RESULT_OK = {'result_code' : 'OK', 'test_results' = [{'id': 1, 'result_code': 'OK', 'memory_bytes': 1000, 'time_ms' : 500}]}



    def test_should_raise_on_no_submit(self):

        with self.assertRaises(ValueError):
            self.tester.test(None)   


    def test_should_pass_proper_params_to_compiler(self):
        self._compiler.compile = MagicMock(return_value = (False, './a.out'))

        self.tester.test(self.SUBMIT)

        self.tester._compiler.compile.assert_called_with(self.SUBMIT['source_path'])



    def test_should_pass_proper_params_to_sandbox(self):
        pass


    def test_code_should_be_compiled_exactly_once(self):

        self._compiler.compile = MagicMock(return_value = (True, './a.out'))

        self.tester.test(self.SUBMIT)

        assertEquals(self._compiler.compile.call_count, 1)


    
    def test_should_return_compilation_error_when_does_not_compile(self):

        self._compiler.compile = MagicMock(return_value = (False, './a.out'))

        result = self.tester.test(self.SUBMIT)

        assertEquals('CE', result['result_code'])



    def test_should_not_probe_on_compilation_error(self):

        self._compiler.compile = MagicMock(return_value = (False, './a.out'))
        self._sandbox.probe = MagicMock(return_value = ({'result_code': 'NA'}))

        self.tester.test(self.SUBMIT)

        assertEquals(self._sandbox.probe.call_count, 0)



    def test_should_not_validate_on_compilation_error(self):

        self._compiler.compile = MagicMock(return_value = (False, './a.out'))
        self._validator1.validate = MagicMock(return_value = None)

        self.tester.test(self.SUBMIT)

        assertEquals(self.__validator1.validate.call_count, 0)


    def test_should_return_cme_code_and_not_tested_on_each_test_on_compilation_error(self):

        self._compiler.compile = MagicMock(return_value = (False, './a.out'))

        result = self.tester.test(self.SUBMIT)

        assertEquals('CME', result['result_code'])

        for test_result in result['test_results']:
            assertEquals('NT', test_result['result_code'])
            assertEquals(0, test_result['memory_bytes'])
            assertEquals(0, test_result['time_ms'])



    def test_should_return_ok_on_submit_with_zero_tests(self):

        self.SUBMIT = { 'id': 123, 'source_path': './main.c', 'tests' : [] }
        self._compiler.compile = MagicMock(return_value = (True, './a.out'))

        result = self.tester.test(self.SUBMIT)

        assertEquals('OK', result['result_code'])



    def test_should_return_ok(self):

        self._compiler.compile = MagicMock(return_value = (true, './a.out'))
        self._sandbox.probe = MagicMock(return_value = self.RESULT_OK);
        self._validator1.validate = MagicMock(return_value = True)

        result = self.tester.test(self.SUBMIT)

        assertEquals('OK', result['result_code'])



    def test_should_return_wrong_answer(self):

        self._compiler.compile = MagicMock(return_value = (true, './a.out'))
        self._sandbox.probe = MagicMock(return_value = self.RESULT_OK);
        self._validator1.validate = MagicMock(return_value = False)

        result = self.tester.test(self.SUBMIT)

        assertEquals('ANS', result['result_code'])



    def test_should_validate_until_ok(self):
        pass

        
    # TODO: Specify behaviour when:
    def testTimeLimitExceeded(self):
        pass
    
    
    def testWrongAnswer(self):
        pass
    
    
    def testCompilationError(self):
        pass
    
    
    def testMemoryLimitExceeded(self):
        pass    
    
    
    def testRuntimeError(self):
        pass

    
if __name__ == '__main__':
    unittest.main()
