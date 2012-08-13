package eu.fabienphoto.BusLineReader.IHM;

import java.util.List;

import eu.fabienphoto.BusLineReader.R;
import eu.fabienphoto.BusLineReader.Model.Line;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Favadapter extends BaseAdapter {

	public List<Line> lines;
	LayoutInflater inflater;
	
	public Favadapter(Context context, List<Line> lines){
		inflater = LayoutInflater.from(context);
		
		this.lines = lines;
	}
	
	
	@Override
	public int getCount() {
		return lines.size();
	}

	@Override
	public Object getItem(int position) {
		return lines.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	private class ViewHolder {
	
		TextView tvcode;
		TextView tvtitre;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder holder;
	
	if(convertView == null) {

		holder = new ViewHolder();
		convertView = inflater.inflate(R.layout.itemfavory, null);
		holder.tvcode = (TextView)convertView.findViewById(R.id.favitemCode);
		holder.tvtitre = (TextView)convertView.findViewById(R.id.favitemTitre);
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}
	holder.tvcode.setText(lines.get(position).getCode());
	holder.tvtitre.setText(lines.get(position).getNom());
	return convertView;
	}

}
