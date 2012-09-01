package eu.fabienphoto.BusLineReader.IHM.LineElement;

import java.util.ArrayList;

import eu.fabienphoto.BusLineReader.R;
import eu.fabienphoto.BusLineReader.Model.Line;
import eu.fabienphoto.BusLineReader.Model.Network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class LinesAdapter extends BaseExpandableListAdapter {
	private ArrayList<Network> groupes;
	private LayoutInflater inflater;



	public LinesAdapter(Context context, ArrayList<Network> groupes) {
		this.groupes = groupes;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}


	@Override
	public Line getChild(int gPosition, int cPosition) {
		return groupes.get(gPosition).getLines().get(cPosition);
	}

	@Override
	public long getChildId(int gPosition, int cPosition) {
		return cPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final Line objet = (Line) getChild(groupPosition, childPosition);

		ChildViewHolder childViewHolder;

		if (convertView == null) {
			childViewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.lineitem, null);
			childViewHolder.tvtitre = (TextView) convertView.findViewById(R.id.LineitemTitre);
			childViewHolder.tvcode = (TextView) convertView.findViewById(R.id.LineitemCode);
			convertView.setTag(childViewHolder);
			
		} else {
			childViewHolder = (ChildViewHolder) convertView.getTag();
		}

		childViewHolder.tvtitre.setText(objet.getNom());
		childViewHolder.tvcode.setText(objet.getCode());

		return convertView;
	}

	@Override
	public int getChildrenCount(int gPosition) {
		return groupes.get(gPosition).getLines().size();
	}

	@Override
	public Network getGroup(int arg0) {
		return groupes.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return groupes.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupViewHolder gholder;

		Network group = (Network) getGroup(groupPosition);

		if (convertView == null) {
			gholder = new GroupViewHolder();
			convertView = inflater.inflate(R.layout.entetelines, null);
			gholder.tvNetwork = (TextView) convertView.findViewById(R.id.TvNetwork);
			convertView.setTag(gholder);
		} else {
			gholder = (GroupViewHolder) convertView.getTag();
		}

		gholder.tvNetwork.setText(group.getName() );
		return convertView;
	}


	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}



	//-------------------------
	class GroupViewHolder {
		public TextView tvNetwork;
	}

	class ChildViewHolder {
		TextView tvcode;
		TextView tvtitre;
	}
}
