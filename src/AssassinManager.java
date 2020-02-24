import java.util.*;

/*
 *	Vincent Do
 * 	Data Structures
 *	Project 3
 * 	Am AssassinManager keeps track of participants in a game of assassin through printing the participant groupings,
 * 	performing kill operations, and determining the current state of the game.
 */
public class AssassinManager
{
	// Living participant names in order of assigned targets.
	private AssassinNode killRing;

	// Deceased participant names ordered by most recent deaths.
	private AssassinNode graveyard;

	/*
	 *	Pre:  List must be non-empty, throws IllegalArgumentException otherwise.
	 *
	 * 	Post: Fills the collection of living participants and assigns targets according to list order.
	 */
	public AssassinManager(List<String> name)
	{
		if (name.isEmpty())
			throw new IllegalArgumentException("List must be non-empty.");
		
		this.killRing = new AssassinNode(name.get(0));
		AssassinNode current = this.killRing;
		
		for (int i = 1; i < name.size(); i++)
		{
			current.next = new AssassinNode(name.get(i));
			current = current.next;
		}
	}

	// Prints the list of living participants and their respective targets.
	public void printKillRing()
	{
		AssassinNode current = this.killRing;
		
		while (current != null)
		{
			System.out.print("    " + current.name + " is stalking ");
			
			if (current.next != null)
				System.out.println(current.next.name);
			else
				System.out.println(this.killRing.name);
			
			current = current.next;
		}
	}

	// Prints the list of deceased participants and their killers by most recent death.
	public void printGraveyard()
	{		
		AssassinNode current = this.graveyard;
		
		while (current != null)
		{
			System.out.println("    " + current.name + " was killed by " + current.killer);
			
			current = current.next;
		}
	}

	/*
	 *	Case-insensitive. Returns true if value is a name contained among
	 * 	the deceased participants, false otherwise.
	 */
	public boolean killRingContains(String name)
	{
		return this.groupContains(this.killRing, name);
	}

	/*
	 *	Case-insensitive. Returns true if value is a name contained among
	 * 	the deceased participants, false otherwise.
	 */
	public boolean graveyardContains(String name)
	{
		return this.groupContains(this.graveyard, name);
	}

	/*
	 *	Searches specified collection of participants for given value. Case-insensitive.
	 * 	Returns true if present, false otherwise.
	 */
	private boolean groupContains(AssassinNode input, String name)
	{
		AssassinNode current = input;

		while (current != null)
		{
			if (current.name.equalsIgnoreCase(name))
				return true;

			current = current.next;
		}

		return false;
	}

	// Evaluates the current state of the game. Returns true if game is concluded, false otherwise.
	public boolean gameOver()
	{
		return this.killRing.next == null;
	}

	// Determines the victor if the game is over. Returns the name of the winner if over, null otherwise.
	public String winner()
	{
		if (gameOver())
			return this.killRing.name;
		else
			return null;
	}

	/*
	 *	Pre:  Value must be a name contained in te list of living participants,
	 * 		  throws IllegalArgumentException otherwise. The game must not have already concluded,
	 * 		  throws IllegalStateException otherwise.
	 *
	 * 	Post: The selected partipant will be transferred from the living group to the front of the
	 * 		  deceased group, and their killer's next target will now be the newly deceased's target.
	 */
	public void kill(String name)
	{
		if (!killRingContains(name))
			throw new IllegalArgumentException("Invalid name.");
		else if (gameOver())
			throw new IllegalStateException("Game already concluded.");
		
		AssassinNode current = this.killRing;
		AssassinNode victim;
		
		if (!current.name.equalsIgnoreCase(name))
		{
			while (!current.next.name.equalsIgnoreCase(name))
				current = current.next;

			victim = current.next;

			if (current.next.next != null)
				current.next = current.next.next;
			else
				current.next = null;
		}
		else
		{
			victim = current;
			
			while (current.next != null)
				current = current.next;
			
			this.killRing = victim.next;
		}
		
		String killer = current.name;
		victim.killer = killer;
		
		victim.next = this.graveyard;
		this.graveyard = victim;
	}
}