package com.example.coordination.messaging;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.coordination.dto.StudentDTO;

@Component
public class StudentListener {

    @RabbitListener(queues = "student.queue") // Asegúrate de que esta cola exista y esté enlazada correctamente
    public void receiveStudent(StudentDTO studentDTO) {
        System.out.println("🎓 Estudiante recibido desde RabbitMQ: " + studentDTO);

        // Aquí puedes guardar en DB, actualizar algo, etc.
    }
}
