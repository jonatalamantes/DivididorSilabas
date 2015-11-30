package Silabeador;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.util.LinkedList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventana extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JTextField textPalabra;
	private JTextField textSilabas;
	private JTextField textTipo;

	public Ventana() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 202);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 0, 422, 36);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblInserteUnaPalabra = new JLabel("Inserte una Palabra:");
		lblInserteUnaPalabra.setBounds(12, 5, 160, 19);
		panel_1.add(lblInserteUnaPalabra);
		
		textPalabra = new JTextField();
		textPalabra.setBounds(162, 5, 248, 19);
		panel_1.add(textPalabra);
		textPalabra.setColumns(10);
				
		JLabel lblSusSlabasSon = new JLabel("Sus sílabas son:");
		lblSusSlabasSon.setBounds(10, 48, 138, 24);
		panel.add(lblSusSlabasSon);
		
		JLabel lblTipoDePalabra = new JLabel("Tipo de palabra:");
		lblTipoDePalabra.setBounds(10, 86, 138, 24);
		panel.add(lblTipoDePalabra);
		
		textSilabas = new JTextField();
		textSilabas.setColumns(10);
		textSilabas.setBounds(137, 51, 248, 19);
		textSilabas.setEditable(false);
		panel.add(textSilabas);
		
		textTipo = new JTextField();
		textTipo.setColumns(10);
		textTipo.setBounds(137, 89, 248, 19);
		textTipo.setEditable(false);
		panel.add(textTipo);
		
		JButton btnEvaluar = new JButton("Evaluar");
		btnEvaluar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				LinkedList<String> silabas = Silabeador.palabraASilabas(textPalabra.getText());
				textSilabas.setText(silabas.toString());
				textTipo.setText(Silabeador.tipoPalabra(silabas));
			}
		});
		btnEvaluar.setBounds(147, 122, 117, 25);
		panel.add(btnEvaluar);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Ventana frame = new Ventana();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
}
