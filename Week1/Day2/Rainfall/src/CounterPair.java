public class CounterPair {
	private Double amount;
	private Integer count;
	
	public CounterPair() {
		amount = 0.0;
		count = 0;
	}
	
	public CounterPair(Double a, Integer c) {
		amount = a;
		count = c;
	}
	
	public void setAmount(Double a) {
		amount = a;
	}
	
	public void setCount(Integer c) {
		count = c;
	}
	
	public void setAmountAndCount(Double a, Integer c) {
		amount = a;
		count = c;
	}
	
	
	public Double getAmount() {
		return amount;
	}
	
	public Integer getCount() {
		return count;
	}
	
	
}
