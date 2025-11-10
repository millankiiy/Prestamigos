package com.prestamigos.usuario.domain.usecase;

import com.prestamigos.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class CorreoService {

    public void enviarCorreoConfirmacion(Usuario usuario) {
        // Para pruebas, solo imprimimos el enlace en consola
        String linkConfirmacion = "http://localhost:5000/api/prestamigos/usuario/confirm?token="
                + usuario.getTokenConfirmacion();
        System.out.println("Enlace de confirmación: " + linkConfirmacion);

        // ✅ Luego puedes reemplazar con JavaMailSender para envío real
    }
}
