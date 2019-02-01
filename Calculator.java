package nine;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator {
	private String actionoperation;   // sluzi u funkciji addCharButton 
	private String operation;
	private String previousoperation;
	private JFrame frame;
	private JPanel panel;
	private JTextField textField;
	private int numero=1;
	private int sum;
	private int pomocna;
	private int brojac=0;
	private String znak;
	private List<Integer> listOfNumbers = new ArrayList(); // sluzi za cuvanje brojeva 
	private boolean operationPressed=true;  // sluzi za pravljenje visecifreni brojeva,jer sve dok ne pritisnemo neku operaciju cekamo na unos broja
	private boolean prekidac=false; //prekidac za provjeru da li je pritisnuto =
	
	private Vector<String> vector = new Vector(); // vektor sluzi za cuvanje operacija 

	private Set<String> setOfOperation = new LinkedHashSet();
	
	public Calculator(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Calculator");
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setEditable(false);
		textField.setPreferredSize(new Dimension(150, 60));
		textField.setFont(new Font("SansSerif", Font.BOLD, 20));
		frame.add(textField, BorderLayout.NORTH);
		panel = new JPanel();
		panel.setLayout(new GridLayout(5,4));
		frame.add(panel, BorderLayout.CENTER);
		addCharButton(panel, "CE");
		addCharButton(panel, "C"); // button for delete
		addCharButton(panel, "<-"); 
		addCharButton(panel, "/"); // button for divide
		
		for (int i = 7; i < 10; i++) {
			//0
			addNumberButton(panel, String.valueOf(i));       // creating numberbutton panel from 7 to 10
		}
		
		addCharButton(panel, "x"); // button for multiple
		
		for (int i = 4; i < 7; i++) {
			//0
			addNumberButton(panel, String.valueOf(i));      // crreating numberbutton panel from 4 to 7
		}
		
		
		addCharButton(panel,  "-");
		
		for (int i = 1; i < 4; i++) {
			//0
			addNumberButton(panel, String.valueOf(i));   // crreating numberbutton panel from 1 to 4
		}
		
		addCharButton(panel, "+");						
		addCharButton(panel, "+-");
		addNumberButton(panel,  String.valueOf(0));		// creatin other operation
		addCharButton(panel, ".");
		addCharButton(panel, "=");
		
		
//		panel = new JPanel();
//		panel.setLayout(new GridLayout(2,3));
//		frame.add(panel, BorderLayout.LINE_START);
		
		vector.add("+");
		
		frame.setVisible(true);
		
		}
	private void buttonClick(Container parent, String name) {
		
		JButton btnclick = new JButton(name);
		btnclick.setActionCommand(name);
		btnclick.addMouseListener(new MouseAdapter());
		
	}
	
	private void addNumberButton(Container parent, String name) {
		JButton numberButton = new JButton(name);
		numberButton.setActionCommand(name);
		numberButton.addActionListener((e)->{  
			
			String action= e.getActionCommand();
			
			int broj = Integer.parseInt(action);
			
			if(!operationPressed) 
				broj= this.pomocna*10 +broj;  // Ovdje zapravo pravimo visecifreni broj 
	
			textField.setText(Integer.toString(broj)); // postavljanje broja u textField
			String pom = textField.getText(); // uzimanje broja iz textFielda sluteci da bi se mogao utipkati visecifreni broj
				
			this.pomocna = Integer.parseInt(pom); // prebacivanje stringa u pomocnu varijablu
				
			if(brojac>0) {   //brojac se ispituje da li je veci od nule jer nema smisla ako nije pritisnuta operacija(odnosno uvecan brojac)
				if(znak=="-") {
					textField.setText(Integer.toString(broj*(-1)));    // za  operaciju minus 
					}
				}
				
			operationPressed=false; // ovdje govorimo da operacija jos nije pritsnuta i da je spreman za pravljenje cifre(lajickim govorom)
				
				//System.out.println(vector.get(0));
				
		});
//				new ActionListener()  {
//
//			@Override
//			public void actionPerformed(ActionEvent event) {
//				String action = event.getActionCommand();
//				textField.setText(action);
//
//				System.out.println("Button " + action + " pressed");
//			}
//		});
		parent.add(numberButton);
	}

		
	private void addCharButton(Container parent, String name) {
		JButton charButton = new JButton(name);
		charButton.setActionCommand(name);
		charButton.addActionListener((e) -> {
			
			if(actionoperation!="="){ 
				previousoperation=actionoperation;   // hvatamo prethodnu operaciju ako nije "="
			}
			
			actionoperation = e.getActionCommand();
			znak=actionoperation; // kupimo uneseni znak odnosno operaciju
			
			if((brojac==0 && actionoperation=="x")|| (brojac==0 && actionoperation=="/"))
				this.sum=1;
			
			
			
			if(!prekidac) { // glavna provjera da li je prekidac ukljucen da ne bi duplirali uneseni broj 
			
			if(actionoperation=="CE") { // prva provjera da li je stisnut CE
				this.sum=0;
				textField.setText(Integer.toString(sum));  // za tipku CE koja nulira kalkulator
				return;
			}
			
			if(actionoperation=="=" && vector.lastElement() =="=")
				vector.removeElementAt(brojac--);
			
			 vector.add(brojac, actionoperation);// dodavanje znaka u vektor
			 
			 if(actionoperation!="=")
				 operation=actionoperation;
				 
			
			String pom = textField.getText(); // trenutna pomocna varijabla kojom kupimo uneseni string iz textFielda
			
			this.numero = Integer.parseInt(pom); // pretvaranje tog unesenog stringa u int
			
			if(brojac==0 && actionoperation=="+-" ) { textField.setText(Integer.toString(this.numero*(-1))); return;} // slucaj kada je brojac=0 i operacija +-
			
			
			if(brojac==0 && actionoperation=="/") { this.sum=this.numero; this.numero=1;}
	
			listOfNumbers.add(this.numero);  // dodavanje broja u niz
				System.out.println("ZNAK ZA PETLJU JE  :      "+ znak );
				
				if(actionoperation=="=") {
					
					
					if(previousoperation=="+" || previousoperation=="-")
						this.sum+=this.numero;
					else if(previousoperation=="/")
						this.sum/=this.numero;
					else 
						this.sum*=this.numero;
					
					
				}
				else {
				
				if(operation=="-"|| operation=="+"||previousoperation=="+"||previousoperation=="-") {
					
					this.sum=this.sum + this.numero;
					//previousoperation="+";
				}
				else if(operation=="x" || previousoperation=="x") {
					this.sum = this.sum * this.numero; 	// glavna operacija kojom definisemo sumu
					//previousoperation="x";
				}
				else
					this.sum/=this.numero;
				}
					
			brojac++; // brojac uvecavamo odnosno brojimo operacije
			
			
			
			textField.setText(Integer.toString(this.sum));
	
			
			operationPressed=true; // prekidamo unos broja 
			
			//System.out.println(brojac);
			
			System.out.println(brojac + ". uneseni broj: "+ listOfNumbers.get(brojac-1));
			System.out.println(brojac + ". unesena operacija: "+ vector.get(brojac-1));
			
			}
			prekidac=false;
			
	
			if(actionoperation=="=") {
				textField.setText(Integer.toString(this.sum));
				prekidac=true; // govori da  je equal pritisnut 
				return;
			}
			if(actionoperation=="+-") {
				sum=sum*(-1);
				textField.setText(Integer.toString(sum));
				prekidac=true;  // govori da +- pritisnut -- sprjecava ulazak prvu petlju
			}
			
		});

		parent.add(charButton);
	}
	
	
	
	
		
		
		public static void main(String[] args) {
			
			Calculator calculator = new Calculator();
			for(int i=0;i<calculator.listOfNumbers.size();i++)
				System.out.println(calculator.listOfNumbers.get(i));
			
		}
		
	
}
