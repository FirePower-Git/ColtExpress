package entities.collectible;

public class Gem extends Collectible {

	public Gem() {
		super();
		this.value = 500;
	}

	public Gem(int value) {
		super(value);
	}

	@Override
	public Collectible.CollectibleType getType() {
		return Collectible.CollectibleType.GEM;
	}


	@Override
	public String toString() {
		return "Gem{" +
				"value=" + this.value +
				'}';
	}

}
