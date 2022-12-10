# Email Service

**A Java Client-Server application to implement an email service, built with JavaFX and with support for multiple instances (you can open multiple windows for the same user).**

The client program support the basic email operation like send an email, reply (/reply all) and forward it with Bootstrap-like design both for the UI and the response alert for every operations and errors (BootstrapFX is used); the server side UI instead plays a role as log of every users operations.

Both the server and client side are built following the MVC architecture based on the Observer-Observable pattern, with thread/threadPools to handle the multiple operation (serve multiple client from server, updating client UI), Socket to build the connections between between the 2 hosts and Synchronized data structure/lock for concurrent operations (writing reading on the email server database).

> This project was made by me and [@OscarNaretto](https://github.com/OscarNaretto) ✨,based on 3rd year (2021/2022) of the bachelor degree on computer science (University of Turin, esame di Programmazione 3).
