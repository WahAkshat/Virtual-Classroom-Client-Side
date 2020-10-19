package sample;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class client_frame extends javax.swing.JFrame
{
    String username, address = "localhost",type;
    ArrayList<String> users = new ArrayList();
    int port = 0007;
    Boolean isConnected = false;
    Socket sock;
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
        String bye = (username + ": :Disconnect");
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
                    "Chat";

            try
            {
                while ((stream = reader.readLine()) != null)
                {
                    data = stream.split(":");
                    if (data[2].equals(chat))
                    {
                        ta_chat.append(data[0] + ": " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
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
                }
            }catch(Exception ex) { }
        }
    }
    //--------------------------//
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        lb_address = new javax.swing.JLabel();
        tf_address = new javax.swing.JTextField("T or F");
        lb_port = new javax.swing.JLabel();
        tf_port = new javax.swing.JTextField();
        lb_username = new javax.swing.JLabel();
        tf_username = new javax.swing.JTextField("Username");
        lb_password = new javax.swing.JLabel();
        tf_password = new javax.swing.JTextField();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        b_anonymous = new javax.swing.JButton();
        b_anonymous.setVisible(false);
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();
        lb_name = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Client's frame");
        setName("client");
        setResizable(false);
        lb_address.setText("Type : ");
        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_addressActionPerformed(evt);
            }
        });
        lb_port.setText("Port :");
        lb_port.setVisible(false);
        tf_port.setText("2222");
        tf_port.setVisible(false);
        tf_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_portActionPerformed(evt);
            }
        });
        lb_username.setText("Username :");
        tf_username.getBorder();
        tf_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usernameActionPerformed(evt);
            }
        });
        lb_password.setText("Password : ");
        tf_password.setText("Enter Password");
        tf_password.getBorder();
        b_connect.setText("Connect");
        b_connect.setBackground(Color.GREEN);
        b_connect.getBorder();
        b_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_connectActionPerformed(evt);
            }
        });
        b_disconnect.setText("Disconnect");
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
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lb_password, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)

                                                        .addComponent(lb_port, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(tf_password)
                                                        .addComponent(tf_port, javax.swing.GroupLayout.DEFAULT_SIZE, 50,
                                                                Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(b_connect)
                                                                .addGap(2, 2, 2)
                                                                .addComponent(b_disconnect)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addComponent(b_anonymous, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                                        .addComponent(lb_port)
                                        .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(b_anonymous))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tf_username)
                                        .addComponent(tf_password)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lb_username)
                                                .addComponent(lb_password)
                                                .addComponent(b_connect)
                                                .addComponent(b_disconnect)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)

                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tf_chat)
                                        .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 31,
                                                Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_name))
        );
        pack();
    }// </editor-fold>
    private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private void tf_portActionPerformed(java.awt.event.ActionEvent evt) {
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
                writer.println(type+" "+username + ":has connected.:Connect");
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
                writer.println(type+" "+ anon + ":has connected.:Connect");
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
                writer.println(type+" "+ username + ":" + tf_chat.getText() + ":" + "Chat");
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
    private javax.swing.JButton b_anonymous;
    private javax.swing.JButton b_connect;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JButton b_send;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_name;
    private javax.swing.JLabel lb_password;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_username;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_chat;
    private javax.swing.JTextField tf_password;
    private javax.swing.JTextField tf_port;
    private javax.swing.JTextField tf_username;
// End of variables declaration
}