package apt.tutorial;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;

import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LunchList extends TabActivity {
  List<Restaurant> model = new ArrayList<Restaurant>();
  ArrayAdapter<Restaurant> adapter;
  EditText etName, etAddr;
  Button btSave;
  RadioGroup type;
  ListView listView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    Log.d("mymsg", "da chay den day1");
    TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
    spec.setContent(R.id.restaurants);
    spec.setIndicator("List",getResources().getDrawable(R.drawable.list));
    getTabHost().addTab(spec);
    spec = getTabHost().newTabSpec("tag2");
    spec.setContent(R.id.details);
    spec.setIndicator("Details",getResources().getDrawable(R.drawable.restaurant));
    getTabHost().addTab(spec);

    getTabHost().setCurrentTab(0);

    etName = (EditText)findViewById(R.id.et_name);
    etAddr = (EditText)findViewById(R.id.et_addr);
    btSave = (Button) findViewById(R.id.save);
    type = (RadioGroup)findViewById(R.id.type);
    Log.d("mymsg", "da chay den day");
    ButtonListener btListener = new ButtonListener();
    btSave.setOnClickListener(btListener);
    listView = (ListView)findViewById(R.id.restaurants);
    adapter = new RestaurantAdapter();
    listView.setAdapter(adapter);

  }
  public class RestaurantAdapter extends ArrayAdapter<Restaurant>{
    RestaurantAdapter() {
      super(LunchList.this,android.R.layout.simple_list_item_1, model);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
      View row = convertView;
      if(row == null) {
        row = getLayoutInflater().inflate(R.layout.row, null);
      }
      TextView tvTitle = (TextView)row.findViewById(R.id.title);
      TextView tvAddr = (TextView)row.findViewById(R.id.address);
      ImageView imgView = (ImageView)row.findViewById(R.id.icon);
      tvTitle.setText(model.get(position).getName());
      tvAddr.setText(model.get(position).getAddress());
      switch(model.get(position).getType()) {
        case "take_out": imgView.setImageResource(R.drawable.ball_yellow);
          break;
        case "shit_down": imgView.setImageResource(R.drawable.ball_red);
          break;
        case "delivery": imgView.setImageResource(R.drawable.ball_green);
          break;
      }
      return row;
    }
  }
  public class ButtonListener implements View.OnClickListener{
    @Override
    public void onClick(View v) {
      Restaurant restaurant = new Restaurant();
      restaurant.setName(etName.getText().toString());
      restaurant.setAddress(etAddr.getText().toString());
      switch(type.getCheckedRadioButtonId()) {
        case R.id.take_out: restaurant.setType("take_out");
          break;
        case R.id.shit_down: restaurant.setType("shit_down");
          break;
        case R.id.delivery: restaurant.setType("delivery");
          break;
      }
      adapter.add(restaurant);
      String msg = restaurant.toString();
      Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();

    }
  }
}
