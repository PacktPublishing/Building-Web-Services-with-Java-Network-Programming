import socket

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.connect(('127.0.0.1', 8080))
client.send(b'Hello, World!')
response = client.recv(4096)
print(response.decode("utf-8"))