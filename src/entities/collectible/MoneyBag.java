package entities.collectible;

public class MoneyBag extends Collectible {

	/**
	 * The constructor of the Moneybag object
	 */
	public MoneyBag() {
		super();
		generateValue();
	}

	/**
	 * The constructor of the Gem object
	 * @param value the value of the collectible
	 */
	public MoneyBag(int value) {
		super(value);
	}

	/**
	 * Generate the value of the collectible
	 */
	@Override
	public void generateValue() {
		this.value = 50 + 50 * (int) (Math.random() * 9.0D - 1E-10);
	}

	/**
	 * function getType return the type of the item (money bag or gem)
	 * @return CollectibleType
	 */
	@Override
	public CollectibleType getType() {
		return CollectibleType.MONEY_BAG;
	}

	/**
	 * Get String of the object
	 * @return String
	 */
	@Override
	public String toString() {
		return "MoneyBag{" +
				"value=" + this.value +
				'}';
	}
}
