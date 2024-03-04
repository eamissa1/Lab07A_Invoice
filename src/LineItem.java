public class LineItem
{
    private Product product;
    private int quantity;

    public LineItem (Product product, int quantity)
    {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalForLineItem()
    {
        return product.getUnitPrice() * quantity;
    }
}
