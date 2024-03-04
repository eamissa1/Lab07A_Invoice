import java.util.ArrayList;
import java.util.List;
public class Invoice
{
    private List<LineItem> lineItemList;

    public Invoice()
    {
        this.lineItemList = new ArrayList<>();
    }

    public void addLineItem(LineItem item)
    {
        lineItemList.add(item);
    }

    public double getTotalAmountDue()
    {
        double total = 0;
        for (LineItem item: lineItemList)
        {
            total += item.getTotalForLineItem();
        }
        return total;
    }

    public List<LineItem> getLineItemList()
    {
        return lineItemList;
    }
}
