package karatsuba;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GuiKaratsuba extends JFrame implements ActionListener, KeyListener {

	private JLabel titulo;
	private JTextField valor1, valor2;
	private JButton ok;

	public GuiKaratsuba() {
		super("Algoritmo Karatsuba");
		setLayout(new BorderLayout());

		JPanel title = new JPanel();
		titulo = new JLabel("Karatsuba");
		titulo.setFont(new Font("Arial", 0, 20));
		title.add(titulo);

		JPanel valores = new JPanel(new FlowLayout());
		valor1 = new JTextField("", 10);
		valor2 = new JTextField("", 10);
		valores.add(valor1);
		valores.add(valor2);

		JPanel botones = new JPanel();
		ok = new JButton("Multiplicar");
		botones.add(ok);

		add(title, BorderLayout.PAGE_START);
		add(valores, BorderLayout.CENTER);
		add(botones, BorderLayout.PAGE_END);

		ok.addActionListener(this);
		valor1.addKeyListener(this);
		valor2.addKeyListener(this);

		setSize(400, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			if (valor1.getText().equals("") || valor2.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Hay un campo vac’o.",
						"Faltan Datos", JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(
						null,
						"Rta: "
								+ karatsuba(new BigInteger(valor1.getText()),
										new BigInteger(valor2.getText())),
						"Resultado", JOptionPane.NO_OPTION);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		int k = (int) e.getKeyChar();
		if (!(k > 47 && k < 58) && !(k == 8 || k == 10)) {
			e.consume();
			JOptionPane.showMessageDialog(null,
					"S—lo se admiten enteros positivos", "Error Datos",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public BigInteger karatsuba(BigInteger a, BigInteger b) {

		// Obtenemos la longitud en bits de los dos operandos
		int aBits = a.bitLength();
		int bBits = b.bitLength();

		// Casos b‡sicos
		if (aBits == 0 || bBits == 0)
			return BigInteger.ZERO;
		if (aBits == 1)
			return b;
		if (bBits == 1)
			return a;

		// Dividir los nœmeros en dos partes
		int n = Math.max(aBits, bBits);
		BigInteger aHigh = a.shiftRight(n / 2);
		BigInteger aLow = a.xor(aHigh.shiftLeft(n / 2));
		BigInteger bHigh = b.shiftRight(n / 2);
		BigInteger bLow = b.xor(bHigh.shiftLeft(n / 2));

		// Hacer las tres multiplicaciones
		BigInteger p1 = karatsuba(aHigh, bHigh);
		BigInteger p2 = karatsuba(aHigh.add(aLow), bHigh.add(bLow));
		BigInteger p3 = karatsuba(aLow, bLow);

		// Desplazar los productos para obtener los tres partes
		BigInteger s1 = p1.shiftLeft(2 * (n / 2));
		BigInteger s2 = p2.subtract(p1).subtract(p3).shiftLeft(n / 2);
		BigInteger s3 = p3;

		// Retorna la suma de las partes
		return s1.add(s2).add(s3);
	}

}
