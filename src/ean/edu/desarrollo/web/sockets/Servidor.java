package ean.edu.desarrollo.web.sockets;
/******************************************************/
/******************************************************/
/******************************************************/
//Configurar un servidor que reciba una conexi�n de un cliente, env�e
//una cadena al cliente y cierre la conexi�n.
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
/******************************************************/
/******************************************************/
/******************************************************/
public class Servidor extends JFrame 
{
	/******************************************************/
	/******************************************************/
	/******************************************************/
	//Atributos de la clase
	private static final long serialVersionUID = 427070726708070735L;
	private JTextField campoIntroducir;
	private JTextArea areaPantalla;
	private ObjectOutputStream salida;
	private ObjectInputStream entrada;
	private ServerSocket servidor;
	private Socket conexion;
	private int contador = 1;
        Factura itemsR = new Factura();
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// configurar GUI
	public Servidor()
	{
		super( "Servidor" );

		Container contenedor = getContentPane();

		// crear campoIntroducir y registrar componente de escucha
		campoIntroducir = new JTextField();
		campoIntroducir.setEditable( false );
		campoIntroducir.addActionListener(new ActionListener() {

					// enviar mensaje al cliente
					public void actionPerformed( ActionEvent evento )
					{
						//enviarDatos( evento.getActionCommand() );
						campoIntroducir.setText( "" );
					}
				}  
		); 

		contenedor.add( campoIntroducir, BorderLayout.NORTH );

		// crear areaPantalla
		areaPantalla = new JTextArea();
		contenedor.add( new JScrollPane( areaPantalla ), 
				BorderLayout.CENTER );

		setSize( 300, 150 );
		setVisible( true );
	} // fin del constructor de Servidor
        
        
        public void hacerFactura(String mensajeR){
            
             Factura produc = new Factura();
             Factura producPrecios = new Factura();
             ArrayList<String> itemPrecios = new ArrayList<String>();
             ArrayList<String> todosProd = new ArrayList<String>();
             ArrayList<String> item = new ArrayList<String>();
             Double calculandoTotal = 0.0;
             int contador = 0 ;
             
             //Para obtener la fecha de la factura
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
             
             String[] numerosComoArray = mensajeR.split("-");

                            for (int i = 0; i < numerosComoArray.length; i++) {
                                System.out.println(numerosComoArray[i]);
                                item.add(numerosComoArray[i]);
                            }

                           // produc.setItemCadena(item);
				
                     
                     System.out.println("Haciendo Factura");
                     
                            for (int i = 0; i < item.size(); i++) {
                                contador++;

                                switch (item.get(i)) {

                                    case "pera":
                                        itemPrecios.add("100");
                                        calculandoTotal = calculandoTotal + 100;
                                        System.out.println("Entro a pera");
                                        break;
                                        
                                     case "manzana":
                                        itemPrecios.add("200");
                                        calculandoTotal = calculandoTotal + 200;
                                        System.out.println("Entro a manzana");
                                        break;
                                         
                                     case "uvas":
                                        itemPrecios.add("300");
                                        calculandoTotal = calculandoTotal + 300;
                                        System.out.println("Entro uvas");
                                        break;
                                         
                                     case "mangos":
                                        itemPrecios.add("400");
                                        calculandoTotal = calculandoTotal + 400;
                                        System.out.println("Entro a mango");
                                        break;  
                                        
                                    case "sandia":
                                        itemPrecios.add("500");
                                        calculandoTotal = calculandoTotal + 500;
                                        System.out.println("Entro a sandia");
                                        break;     
                                }  
                                

                            }
                            
                            
                            
                            
                            for(int i =0; i <item.size(); i++){
                               
                                todosProd.add(item.get(i));
                                
                                System.out.println("Armando array >>>i");
                                for(int j=0; j < itemPrecios.size(); j++){
                                    if(i==j){
                                    todosProd.add(itemPrecios.get(j));
                                    System.out.println("Armando array >>>j");
                                    }
 
                                }                                
                            }
                            producPrecios.setItemCadena(todosProd);
                            producPrecios.setTotalFactura(calculandoTotal);
                            producPrecios.setFechaFactura(c.getTime());
                            producPrecios.setNumeroCliente("9854879");
                            
                           enviarDatos(producPrecios.toString());
                            
                            
            
        }
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// configurar y ejecutar el servidor 
	public void ejecutarServidor()
	{
		// configurar servidor para que reciba conexiones; procesar las conexiones
		try {

			// Paso 1: crear un objeto ServerSocket.
			servidor = new ServerSocket(  12345, 100 );

			while ( true ) {

				try {
					esperarConexion(); // Paso 2: esperar una conexi�n.
					obtenerFlujos();        // Paso 3: obtener flujos de entrada y salida.
					procesarConexion(); // Paso 4: procesar la conexi�n.
				}

				// procesar excepci�n EOFException cuando el cliente cierre la conexi�n 
				catch ( EOFException excepcionEOF ) {
					System.err.println( "El servidor termino la conexion" );
				}

				finally {
					cerrarConexion();   // Paso 5: cerrar la conexi�n.
					++contador;
				}

			} // fin de instrucci�n while

		} // fin del bloque try

		// procesar problemas con E/S
		catch ( IOException excepcionES ) {
			excepcionES.printStackTrace();
		}

	} // fin del m�todo ejecutarServidor
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// esperar que la conexi�n llegue, despu�s mostrar informaci�n de la conexi�n
	private void esperarConexion() throws IOException
	{
		mostrarMensaje( "Esperando una conexion\n" );
		conexion = servidor.accept(); // permitir al servidor aceptar la conexi�n            
		mostrarMensaje( "Conexion " + contador + " recibida de: " +
				conexion.getInetAddress().getHostName() );
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// obtener flujos para enviar y recibir datos
	private void obtenerFlujos() throws IOException
	{
		// establecer flujo de salida para los objetos
		salida = new ObjectOutputStream( conexion.getOutputStream() );
		salida.flush(); // vaciar b�fer de salida para enviar informaci�n de encabezado

		// establecer flujo de entrada para los objetos
		entrada = new ObjectInputStream( conexion.getInputStream() );

		mostrarMensaje( "\nSe recibieron los flujos de E/S\n" );
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// procesar la conexi�n con el cliente
	private void procesarConexion() throws IOException
	{
		// enviar mensaje de conexi�n exitosa al cliente
		String mensaje = "Conexion exitosa";
            //Factura mensaje = new Factura();
		enviarDatos( mensaje );

		// habilitar campoIntroducir para que el usuario del servidor pueda enviar mensajes
		establecerCampoTextoEditable( true );

		do { // procesar los mensajes enviados por el cliente

			// leer el mensaje y mostrarlo en pantalla
			try {
                           
				mensaje = ( String ) entrada.readObject();
                                
                                                                
                               hacerFactura(mensaje);
                       
				mostrarMensaje( "\n Estos son los items enviados por el cliente" + mensaje );
                                
                                
			}

			// atrapar problemas que pueden ocurrir al tratar de leer del cliente
			catch ( ClassNotFoundException excepcionClaseNoEncontrada ) {
				mostrarMensaje( "\nSe recibio un tipo de objeto desconocido" );
			}

		} while ( !mensaje.equals( "CLIENTE>>> TERMINAR" ) );

	} // fin del m�todo procesarConexion
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// cerrar flujos y socket
	private void cerrarConexion() 
	{
		mostrarMensaje( "\nFinalizando la conexion\n" );
		establecerCampoTextoEditable( false ); // deshabilitar campoIntroducir

		try {
			salida.close();
			entrada.close();
			conexion.close();
		}
		catch( IOException excepcionES ) {
			excepcionES.printStackTrace();
		}
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// enviar mensaje al cliente
	private void enviarDatos( String mensaje )
	{
		// enviar objeto al cliente
		try {
			salida.writeObject(mensaje );
			salida.flush();
			mostrarMensaje( "\nSERVIDOR>>> " + mensaje );
                        salida.writeObject("\nADVERTENCIA: Ingrese Los Productos Separados por (-) " );
                        salida.writeObject("\n*******************************************" );
                        salida.writeObject("\nSOLO HAY A LA VENTA LAS SIGUIENTES FRUTAS" );
                        salida.writeObject("\npera" );
                        salida.writeObject("\nmanzana" );
                        salida.writeObject("\nuvas" );
                        salida.writeObject("\nmangos" );
                        salida.writeObject("\nsandia" );
                        salida.writeObject("\n*******************************************" );
		}

		// procesar problemas que pueden ocurrir al enviar el objeto
		catch ( IOException excepcionES ) {
			areaPantalla.append( "\nError al escribir objeto " +  excepcionES.getMessage());
		}
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// m�todo utilitario que es llamado desde otros subprocesos para manipular a
	// areaPantalla en el subproceso despachador de eventos
	private void mostrarMensaje( final String mensajeAMostrar )
	{
		// mostrar mensaje del subproceso de ejecuci�n despachador de eventos
		SwingUtilities.invokeLater(
				new Runnable() {  // clase interna para asegurar que la GUI se actualice apropiadamente

					public void run() // actualiza areaPantalla
					{
						areaPantalla.append( mensajeAMostrar );
						areaPantalla.setCaretPosition( 
								areaPantalla.getText().length() );
					}

				}  // fin de la clase interna

		); // fin de la llamada a SwingUtilities.invokeLater
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// m�todo utilitario que es llamado desde otros subprocesos para manipular a 
	// campoIntroducir en el subproceso despachador de eventos
	private void establecerCampoTextoEditable( final boolean editable )
	{
		// mostrar mensaje del subproceso de ejecuci�n despachador de eventos
		SwingUtilities.invokeLater(
				new Runnable() {  // clase interna para asegurar que la GUI se actualice apropiadamente

					public void run()  // establece la capacidad de modificar a campoIntroducir
					{
						campoIntroducir.setEditable( editable );
					}

				}  // fin de la clase interna

		); // fin de la llamada a SwingUtilities.invokeLater
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	public static void main( String args[] )
	{
		Servidor aplicacion = new Servidor();
		aplicacion.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		aplicacion.ejecutarServidor();
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
}  // fin de la clase Servidor