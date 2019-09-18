package ticketmachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@Before
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : message si erreur, valeur attendue, valeur réelle
		assertEquals("Initialisation incorrecte du prix", PRICE, machine.getPrice());
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals("La balance n'est pas correctement mise à jour", 10 + 20, machine.getBalance()); // Les montants ont été correctement additionnés               
	}
        
        @Test
        // S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
        public void NotPrint() {
                machine.insertMoney(10);
                machine.insertMoney(20);
                assertFalse("Montant insuffisant", machine.printTicket());
        }
        
        @Test
        // S4 : on imprime le ticket si le montant inséré est suffisant
        public void Print() {
                machine.insertMoney(20);
                machine.insertMoney(20);
                machine.insertMoney(10);
                assertEquals("Montant suffisant, impression du ticket", machine.getBalance(), machine.getPrice());
        }
        
        @Test
        // S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
        public void Print_And_Decrease() {
                machine.insertMoney(20);
                machine.insertMoney(20);
                machine.insertMoney(20);
                machine.printTicket();
                assertEquals("--balance-- se m'est à jour correctement après impression d'un ticket", 10, machine.getBalance());
        }
        
        @Test
        // S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
        public void Amount_Collected() {
                machine.insertMoney(20);
                machine.insertMoney(20);
                machine.insertMoney(10);
                assertEquals("--total-- est mis à jour à chaque impression (+ --price-- par impression)", 0, machine.getTotal());
                machine.printTicket();
                machine.insertMoney(20);
                machine.insertMoney(20);
                machine.insertMoney(10);
                machine.printTicket();
                assertEquals("--total-- est mis à jour à chaque impression (+ --price-- par impression)", 100, machine.getTotal());
        }
        
        @Test
        //  S7 : refund() rend correctement la monnaie
        public void Correct_Refund() {
                machine.insertMoney(20);
                machine.insertMoney(20);
                assertEquals("La machine rend correctement la monnaie (--balance-- après impression)", 40, machine.refund());
        }
        
        @Test
        //  S8 : refund() remet la balance à zéro
        public void Refund_Balance() {
                machine.insertMoney(20);
                machine.insertMoney(20);
                machine.refund();
                assertEquals("La machine remet --balance-- à zéro", 0, machine.getBalance());
        }
        
        @Test (expected = IllegalArgumentException.class)
        //  S9 : On ne peut pas insérer un montant négatif
        public void No_Negative_Balance() {
                machine.insertMoney(-10);
        }
        
        @Test (expected = IllegalArgumentException.class)
        //  S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
        public void No_Machine_Negative() {
                TicketMachine bug = new TicketMachine(-10);
        }
}
