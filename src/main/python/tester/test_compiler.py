#!/usr/bin/python
import os
import unittest
from compiler import CCompiler

class TestCCompiler(unittest.TestCase):

    def setUp(self):
        self.compiler = CCompiler()

        self.FILE_PATH = { 'OK': 'ok.c', 'WARN': 'warn.c', 'ERROR': 'error.c' }
        self.FILE_SOURCE = { 'OK': 'int main() { return 0; }\n', 'WARN': 'int main() { }\n', 'ERROR': '\n' }
        
        # Create all helper source files and populate them with source code
        for key in self.FILE_PATH:
            with open(self.FILE_PATH[key], 'w') as f: f.write(self.FILE_SOURCE[key])


    def test_should_compile_properly(self):
        (result, exec_path) = self.compiler.compile(self.FILE_PATH['OK'])
        
        self.assertTrue(result)
        self.assertTrue(os.path.exists(exec_path))

        os.remove(exec_path)


    def test_should_compile_properly_with_some_standard_flags(self):
        (result, exec_path) = self.compiler.compile(self.FILE_PATH['OK'], ['-O2', '-Wall'])
        
        self.assertTrue(result)
        self.assertTrue(os.path.exists(exec_path))

        os.remove(exec_path)


    def test_should_compile_properly_ignoring_warn(self):
        (result, exec_path) = self.compiler.compile(self.FILE_PATH['WARN'])
        
        self.assertTrue(result)
        self.assertTrue(os.path.exists(exec_path))

        os.remove(exec_path)


    def test_should_place_exec_file_where_pointed(self):
        EXEC_PATH = 'a.out'

        (result, exec_path) = self.compiler.compile(self.FILE_PATH['OK'], exec_path = EXEC_PATH)

        self.assertEquals(EXEC_PATH, exec_path)

        os.remove(exec_path)


    def test_should_replace_old_executable(self):
        EXEC_PATH = './a.out'

        (result, exec_path1) = self.compiler.compile(self.FILE_PATH['OK'], exec_path = EXEC_PATH)
        self.assertTrue(os.path.exists(exec_path1))

        (result, exec_path2) = self.compiler.compile(self.FILE_PATH['WARN'], exec_path = EXEC_PATH)        
        self.assertTrue(os.path.exists(exec_path2))

        self.assertEquals(exec_path1, exec_path2)

        os.remove(EXEC_PATH)


    def test_should_raise_on_source_path_not_found(self):        

        # Clearly there is no such file as empty string
        NONEXISTANT_FILE = ''

        with self.assertRaises(IOError):
            self.compiler.compile(NONEXISTANT_FILE)


    def test_should_raise_on_no_source_path_provided(self):        

        with self.assertRaises(TypeError):
            self.compiler.compile(None)


    def test_should_raise_on_no_exec_path_provided(self):        

        with self.assertRaises(TypeError):
            self.compiler.compile(self.FILE_PATH['ERROR'], exec_path = None)


    def test_should_raise_on_empty_exec_path_provided(self):        

        with self.assertRaises(ValueError):
            self.compiler.compile(self.FILE_PATH['ERROR'], exec_path = '')            


    def test_should_return_compilation_error_and_remove_executable_if_any(self):

        (result, exec_path) = self.compiler.compile(self.FILE_PATH['ERROR'])

        self.assertFalse(result)
        self.assertFalse(os.path.exists(exec_path))


    def tearDown(self):
        self.compiler = None

        # Remove all files created in setUp
        for filename in self.FILE_PATH.itervalues():
            if os.path.exists(filename): os.remove(filename)


if __name__ == '__main__':
    unittest.main()
