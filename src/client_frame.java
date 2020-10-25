import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class client_frame extends javax.swing.JFrame
{

    String username, address = "localhost",type;
    ArrayList<String> users = new ArrayList();
    int port = 0007;
    Boolean isConnected = false;
    static Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    //--------------------------//
    public void ListenThread()
    {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }
    //--------------------------//
    public void userAdd(String data)
    {
        users.add(data);
    }
    //--------------------------//
    public void userRemove(String data)
    {
        ta_chat.append(data + " is now offline.\n");
    }
    //--------------------------//
    public void writeUsers()
    {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        //users.append(token + "\n");
    }
    //--------------------------//
    public void sendDisconnect()
    {
        String bye = (username + "- -Disconnect");
        try
        {
            writer.println(bye);
            writer.flush();
        } catch (Exception e)
        {
            ta_chat.append("Could not send Disconnect message.\n");
        }
    }
    //--------------------------//
    public void Disconnect()
    {
        try
        {
            ta_chat.append("Disconnected.\n");
            sock.close();
        } catch(Exception ex) {
            ta_chat.append("Failed to disconnect. \n");
        }
        isConnected = false;
        tf_username.setEditable(true);
    }

   static String File_name;
   static String Location;

    public static void sendFile() {
        try {

            File myFile = new File(Location);
            byte[] mybytearray = new byte[(int) myFile.length()];
            if(!myFile.exists()) {
                System.out.println("File does not exist..");
                return;
            }

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //bis.read(mybytearray, 0, mybytearray.length);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            System.out.println(Arrays.toString(mybytearray));

            OutputStream os = sock.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            System.out.println("File "+Location+" sent to Server.");
        } catch (Exception e) {
            System.err.println("Exceptionnnn: "+e);
        }
    }

    public void receiveFile() {
        try {

            int bytesRead;

            InputStream in = sock.getInputStream();

            DataInputStream clientData = new DataInputStream(in);

            System.out.println(File_name);

            OutputStream output = new FileOutputStream(File_name);

            byte[] buffer = new byte[1024];

            int a = clientData.read(buffer);
            System.out.println(Arrays.toString(buffer));
            for(byte b:buffer){
                if(b!=0)
                    output.write(b);
            }

            output.flush();

            System.out.println("File "+File_name+" received from Server.");
           } catch (IOException ex) {
            System.out.println("Exception: "+ex);
        }

        ListenThread();



    }
    public client_frame()
    {
        initComponents();
    }
    //--------------------------//
    public class IncomingReader implements Runnable
    {
        @Override
        public void run()
        {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat =
                    "Chat",send="Send",receive="Receive";

            try
            {
                while ((stream = reader.readLine()) != null)
                {
                    data = stream.split("-");

                    if (data[2].equals(chat))
                    {
                        ta_chat.append(data[0] + "- " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                        System.out.println(ta_chat.getText());
                    }
                    else if (data[2].equals(connect))
                    {
                        ta_chat.removeAll();
                        userAdd(data[0]);
                    }
                    else if (data[2].equals(disconnect))
                    {
                        userRemove(data[0]);
                    }
                    else if (data[2].equals(done))
                    {
                        writeUsers();
                        users.clear();
                    }

                    else if (data[2].equals(send)) {
                        System.out.println("Yes send till this line 181");
                        ta_chat.append(data[0] + "-" + data[1] + "-" + send +"\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                        System.out.println(ta_chat.getText());

                    }
                    else if (data[2].equals(receive)) {
                        System.out.println("Yes received at this line 188");
                        System.out.println(data[1]);
                        ta_chat.append(data[0] + "-" + data[1] + "-" + receive +"\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                        System.out.println(ta_chat.getText());
                                            }
                }
            }catch(Exception ex) { }
        }
    }


    private void initComponents() {
        lb_address = new javax.swing.JLabel();
        lb_Name_url=new javax.swing.JLabel("FILE-DETAILS :");
        tf_Name_url=new javax.swing.JTextField("NAME/LOC");
        tf_address = new javax.swing.JTextField("T or S");
        lb_username = new javax.swing.JLabel();
        tf_username = new javax.swing.JTextField("NAME");
        b_sendFile = new javax.swing.JButton();
        b_receiveFile = new javax.swing.JButton();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();
        lb_name = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Client's frame");
        setName("client");
        setResizable(false);

        lb_address.setText("TYPE : ");
//        tf_address.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                tf_addressActionPerformed(evt);
//            }
//        });
        lb_username.setText("NAME :");
        tf_username.getBorder();
        tf_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usernameActionPerformed(evt);
            }
        });

        b_sendFile.setText("SEND FILE");
        b_sendFile.setBackground(Color.MAGENTA);
        b_sendFile.getBorder();
        b_sendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendFileActionPerformed(evt);
            }
        });

        b_receiveFile.setText("RECEIVE FILE");
        b_receiveFile.setBackground(Color.CYAN);
        b_receiveFile.getBorder();
        b_receiveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    b_receiveFileActionPerformed(evt);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        b_connect.setText("CONNECT");
        b_connect.setBackground(Color.GREEN);
        b_connect.getBorder();
        b_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_connectActionPerformed(evt);
            }
        });
        b_disconnect.setText("DISCONNECT");
        b_disconnect.setBackground(Color.red);
        b_disconnect.getBorder();
        b_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });


        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);
        b_send.setText("SEND");
        b_send.setBackground(Color.GREEN);
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });
        lb_name.setText("OS PROJECT");
        lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0,
                0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 352,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 111,
                                                        Short.MAX_VALUE))

                                        .addComponent(jScrollPane1)

                                        .addGroup(layout.createSequentialGroup()


                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(lb_username, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                62, Short.MAX_VALUE)
                                                        .addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))

                                                .addGap(18, 18, 18)

                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(tf_address, javax.swing.GroupLayout.DEFAULT_SIZE, 89,
                                                                Short.MAX_VALUE)

                                                        .addComponent(tf_username))

                                                .addGap(18, 18, 18)

                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(lb_Name_url, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                62, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(tf_Name_url, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                62, Short.MAX_VALUE))






                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)

                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()

                                                                .addComponent(b_connect)
                                                                .addGap(50, 50, 50)
                                                                .addComponent(b_disconnect)
                                                                .addGap(50, 50, 50))

                                                )

                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()

                                                                .addComponent(b_sendFile)
                                                                .addGap(50, 50, 50)
                                                                .addComponent(b_receiveFile)
                                                                .addGap(50, 50, 50))

                                                )

                                        )

                                )
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                layout.createSequentialGroup()
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lb_name)
                                        .addGap(201, 201, 201))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lb_address)
                                        .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lb_Name_url)
                                        .addComponent(tf_Name_url))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tf_username)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lb_username)
                                                .addComponent(b_connect)
                                                .addComponent(b_disconnect)
                                                .addComponent(b_sendFile)
                                                .addComponent(b_receiveFile)
                                        ))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)

                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tf_chat)
                                        .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 45,
                                                Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_name))
        );
        pack();
    }// </editor-fold>
    //    private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {
//    }
    private void b_sendFileActionPerformed(java.awt.event.ActionEvent evt) {
        Location=tf_Name_url.getText();


        try {
            writer.println(type+" "+ username + "-" + Location + "-" + "Send");
            writer.flush(); // flushes the buffer
        }
        catch (Exception ex) {
            ta_chat.append("File was not sent. \n");
        }

        tf_Name_url.setText("");
        tf_Name_url.requestFocus();

        sendFile();

    }


    private void b_receiveFileActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
        File_name=tf_Name_url.getText();


        try {
            writer.println(type+" "+ username + "-" + tf_Name_url.getText() + "-" + "Receive");
            writer.flush(); // flushes the buffer
            System.out.println("done till here");
        }
        catch (Exception ex) {
            ta_chat.append("File was not received. \n");
        }

        tf_Name_url.setText("");
        tf_Name_url.requestFocus();

        receiveFile();

    }
    private void tf_usernameActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {
        if (isConnected == false)
        {
            type = tf_address.getText();
            username = tf_username.getText();
            tf_username.setEditable(false);
            try
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(type+" "+username + "-has connected.-Connect");
                writer.flush();
                isConnected = true;
            }
            catch (Exception ex)
            {
                ta_chat.append("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }
            ListenThread();
        } else if (isConnected == true)
        {
            ta_chat.append("You are already connected. \n");

        }
    }
    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {
        sendDisconnect();
        Disconnect();
    }
    private void b_anonymousActionPerformed(java.awt.event.ActionEvent evt) {
        tf_username.setText("");
        if (isConnected == false)
        {
            String anon="anon";
            Random generator = new Random();
            int i = generator.nextInt(999) + 1;
            String is=String.valueOf(i);
            anon=anon.concat(is);
            username=anon;
            tf_username.setText(anon);
            tf_username.setEditable(false);
            try
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(type+" "+ anon + "-has connected.-Connect");
                writer.flush();
                isConnected = true;
            }
            catch (Exception ex)
            {
                ta_chat.append("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }
            ListenThread();
        } else if (isConnected == true)
        {
            ta_chat.append("You are already connected. \n");
        }
    }
    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {
        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {

            try {
                writer.println(type+" "+ username + "-" + tf_chat.getText() + "-" + "Chat");
                writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                ta_chat.append("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }
        tf_chat.setText("");
        tf_chat.requestFocus();
    }
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                client_frame cl = new client_frame();
                cl.setVisible(true);
                cl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                cl.setPreferredSize(new Dimension(550, 300));


                cl.getContentPane().setBackground(Color.ORANGE);
            }
        });
    }


    // Variables declaration - do not modify
    private javax.swing.JButton b_sendFile;
    private javax.swing.JButton b_receiveFile;
    private javax.swing.JButton b_connect;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JButton b_send;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_Name_url;
    private javax.swing.JTextField tf_Name_url;
    private javax.swing.JLabel lb_name;
    private javax.swing.JLabel lb_username;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_chat;
    private javax.swing.JTextField tf_username;
// End of variables declaration
}