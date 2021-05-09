package entities.collectible;

public class Gem extends Collectible {

	/**
	 * The constructor of the Gem object
	 */
	public Gem() {
		super();
		this.value = 500;
	}

	/**
	 * The constructor of the Gem object
	 * @param value the value of the collectible
	 */
	public Gem(int value) {
		super(value);
	}

	/**
	 * function getType return the type of the item (money bag or gem)
	 * @return CollectibleType
	 */
	@Override
	public Collectible.CollectibleType getType() {
		return Collectible.CollectibleType.GEM;
	}

	/**
	 * Get String of the object
	 * @return String
	 */
	@Override
	public String toString() {
		return "Gem{" +
				"value=" + this.value +
				'}';
	}

}
