package business;

import entities.Utils;

public class HiloAnalizador implements Runnable {

    private String message ;
    private MailBL mailBL;

    public HiloAnalizador(String message){
        this.message = message;
        mailBL = new MailBL();
    }

    @Override
    public void run() {
        String subject = "";
        String from = "";
        boolean sw = false;
        for (int i = 0; i < message.length(); i++) {
            if (sw) {
                if (message.charAt(i) != '>') {
                    from = from + message.charAt(i);
                } else {
                    sw = false;
                    break;
                }
            } else {
                if (message.charAt(i) == '<') {
                    sw = true;
                }
            }
        }

        String[] aux = message.split("Subject: ");
        for (int i = 1; i < aux.length; i++) {
            subject = subject + aux[i];
        }

        System.out.println(subject);
        System.out.println(from);

        Analizar(subject, from);
    }

    public void Analizar(String sAsunto, String sCorreo) {
        String[] split = sAsunto.split(" ");

        String sMensaje = "";

        for (int i = 2; i < split.length; i++) {
            sMensaje = sMensaje + split[i];
        }

        try {
            switch (split[0]) {
                case Utils.Accion.Cliente:
                    ClienteBL cliente = new ClienteBL();
                    cliente.Analizar(sAsunto, sMensaje, sCorreo);
                    break;
                case Utils.Accion.Geocerca:
                    GeocercaBL geocerca = new GeocercaBL();
                    geocerca.Analizar(sAsunto, sMensaje, sCorreo);
                    break;
                case Utils.Accion.Perfil:
                    PerfilBL perfil = new PerfilBL();
                    perfil.Analizar(sAsunto, sMensaje, sCorreo);
                    break;
                case Utils.Accion.Posicion:
                    PosicionBL posicion = new PosicionBL();
                    posicion.Analizar(sAsunto, sMensaje, sCorreo);
                    break;
                case Utils.Accion.Puntos_Interes:
                    Puntos_InteresBL puntos = new Puntos_InteresBL();
                    puntos.Analizar(sAsunto, sMensaje, sCorreo);
                    break;

                default:
                    mailBL.SendMail(sCorreo, "Correo de Ayuda", Utils.Accion.MenuAyuda);
                    break;
            }
        } catch (Exception ex) {
            try {
                String mensaje = ex.getMessage() +" " + ex.getStackTrace().toString();

                mailBL.SendMail(sCorreo, "Correo de Ayuda", Utils.Accion.MenuAyuda + "\n \n WFT!! Sorry was happened a error. \n"+ mensaje );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
