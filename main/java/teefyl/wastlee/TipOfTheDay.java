package teefyl.wastlee;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class TipOfTheDay extends AppCompatActivity {
    Button tipButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_of_the_day);
        setupActionBar();

        Random rand = new Random();
        TextView tv = (TextView) findViewById(R.id.TextView02);
        tv.setText(QUOTES[rand.nextInt(QUOTES.length)]);

        tipButt = (Button) findViewById(R.id.tipBut);

        tipButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                TextView tv = (TextView) findViewById(R.id.TextView02);
                tv.setText(QUOTES[rand.nextInt(QUOTES.length)]);

            }

        });
    }

    public static final String QUOTES[] = {
            "Plan your meals for the week and then shop only for those meals. ",
            "Buy non perishable items in bulk, buy the minimum perishable items you need.",
            "Shop more frequently and buy less!",
            "Freeze extra herbs/vegetables that you won't use.",
            "Keep leftover avocado halves green by storing them with a cut onion to keep them from browning for one to two days. " +
                    "The sulfur compounds in the onion prevents oxidation.",
            "Wrap cheese loosely in waxed paper before putting it back in the plastic packaging, and only partially seal " +
                    "the plastic. This prevents ammonia and bacteria from being trapped and keeps your cheese from absorbing " +
                    "chemicals and odors from the packaging.",
            "Add oil, vinegar, herbs and spices to a near-empty mayo jar, and shake vigorously for an " +
                    "impromptu salad dressing that gets every last bit of the mayo.",
            "Freeze leftover chili and soups by lining the bottoms of the bowls you eat from with plastic wrap" +
                    " and pouring the chili in. Once it's frozen solid, pull it out of the bowls, and store it in " +
                    "a freezer bag in individual servings — it will fit right back in your bowl.",
            "Instead of discarding the green tops of carrots, beats and radishes, use them in stir-fries," +
                    " pestos or even as stand-alone sides.",
            "Before your herbs go bad, chop them up, and mix them with butter or olive oil. Place the " +
                    "mixture in ice cube trays, and once they're frozen, place them in a freezer bag. Use " +
                    "them to sauté onions, season popcorn or steamed veggies, or defrost to spread on toast.",
            "Don't toss stale bread. Turn it into homemade croutons!",
            "Don't toss eggs because you're not sure. Find out if they're fresh by floating them gently in a bowl" +
                    " of water. The ones that sink are still fresh.",
            "Store onions and garlic in unused knee-high stockings to keep them fresher.",
            "Store apples and potatoes together to prevent the latter from sprouting.",
            "Toss a few marshmallows in with brown sugar to keep it from getting hard or to soften it.",
            "When you're cutting peppers, potatoes, tomatoes and more, instead of tossing the ends and other " +
                    "edible bits that don't measure up to the current dish, cut them into dice-size pieces, and " +
                    "freeze them to use in a quick pasta, omelet or rice dish during the week.",
            "Wrap the stem of a bunch of bananas in plastic wrap to keep them fresher longer.",
            "Herbs, green onions and asparagus all benefit from being stored root- or stem-end down in water. Cover the exposed ends if necessary, and store in the fridge.",
            "Trim the leafy tops off your celery to keep them fresher. But save those leaves for a tasty salad add-in.",
            "Stale chips or crackers? Refresh them in the microwave or oven.",
            "Don't throw out that Nutella jar! Warm a little milk, pour it into the jar (with a little cinnamon or nutmeg if you want), screw the cap back on, and shake vigorously to make a hazelnut-flavored hot cocoa.",
            "Plan Your Meals\n" +
                    "Be realistic. How many dinners will you really cook at home?\n" +
                    "\n" +
                    "How much food does everyone at the table eat? Plan as many meals as you can, considering what's already in the kitchen and making use of any ingredients that need to be used up sooner rather than later.",
            "Practice First-In, First-Out (FIFO)\n" +
                    "If anyone knows about keeping a lid on food costs, it's restaurants. Follow their example by using older items first. An organized fridge makes this possible: put new purchases behind the food that needs to be eaten first.",
            "Cook Realistically\n" +
                    "It's one thing to purposefully make extra food to freeze and enjoy later, it's quite another to consistently cook up a full pound of pasta when your family only eats 3/4 of a pound. Think about what you throw away after different meals and adjust your cooking accordingly.",
            "Eat Leftovers\n" +
                    "Sure, some people claim to hate them, but throwing away perfectly good, already cooked food is a serious waste of resources and time. Try heating things up in new ways: melt some cheese on top, chop it up and fry up with some rice, use leftovers to fill homemade burritos or pile them on some brown rice for a delicious grain bowl.",
            "Don't Overserve\n" +
                    "Better to go for seconds than to throw away uneaten food on a plate.",
            "Don't Be a Slave to Sell-By Dates\n" +
                    "Properly handled and stored food can be perfectly good to eat well past the sell-by date stamped on it. If things look good and smell good and taste good, they're probably good. You don't need a date to tell you milk is sour nor that cheese is moldy, throw those items, but eat the still-good ones.",
            "Track What You Throw Away\n" +
                    "This will help you make better, less-wasteful shopping lists going forward (and back to step 1 above).\n" +
                    "\n" +
                    "There is always going to be a certain amount of food waste, put yours to work and learn about composting!",

    };

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
