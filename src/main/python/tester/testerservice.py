#!/usr/bin/python
import json
import socket
import tester

# TODO: Sending response
class TesterService(object):

    def __init__(self, host = '', port = 5000):
        self._socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.address = (host, port)

    
    def _retrieve_request(self, client_socket):
        '''
        Reads data from socket and parses it to json.
        Assumes that json contains "submit" and "response_address"
        '''
        data = None

        while 1:
            chunk = client_socket.recv(256)
            if not chunk: break
            data += chunk

        json = json.loads(data)

        return json["submit"], json["response_address"]


    def start(self):
        self._socket.bind(self.address)
        self._socket.listen(5)

        while 1:
            client_socket, address = self._socket.accept()

            submit = None
            response_address = None
            result_code = None
            test_results = None

            try:
                submit, response_address = self._retrieve_request(client_socket)
                print json.dumps(request, sort_keys = True, indent = 4)
                
                t = tester.Tester()
                result = t.test(submit)

            except Exception, e:
                self._send_response(self._parse_response(error));

            else:
                pass
            finally:
                client_socket.close()



def main():
    server = TesterService()
    server.start()


if __name__ == '__main__':
    main()
