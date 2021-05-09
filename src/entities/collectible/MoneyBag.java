package entities.collectible;

public class MoneyBag extends Collectible {

	public MoneyBag() {
		super();
		generateValue();
	}

	public MoneyBag(int value) {
		super(value);
	}

	@Override
	public void generateValue() {
		this.value = 50 * (int) (Math.random() * 8.0D - 1E-10);
	}

	@Override
	public CollectibleType getType() {
		return CollectibleType.MONEY_BAG;
	}

	@Override
	public String toString() {
		return "MoneyBag{" +
				"value=" + this.value +
				'}';
	}
}
