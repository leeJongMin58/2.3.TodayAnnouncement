package com.jomiroid.todayannouncement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            val port = 8080
            val server = ServerSocket(port)

            val socket = server.accept()

            socket.getInputStream()     // 클라이언트로 부터 들어오는 스트림 == 클라이언트의 socket.outputStream
            socket.getOutputStream()    // 클라이언트에게 데이터를 주는 스트림 == socket.inputStream


            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var input: String = "-1"

            while (input != null && input !="") {
                input = reader.readLine()
            }

            Log.e("SERVER", "READ DATA $input")

            printer.println("HTTP/1.1 200 OK")
            printer.println("Contetnt-Type: text/html\r\n")

            printer.println("<h1>Hello World</h1>")
            printer.println("\r\n")
            printer.flush()
            printer.close()

            reader.close()

            socket.close()
        }
    }
}
