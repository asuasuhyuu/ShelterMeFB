package io.github.seibelsabrina.sheltermefb;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by seibelsabrina on 2/28/18.
 */

public class ShelterList extends ArrayAdapter<Shelter> {
    private Activity context;
    private List<Shelter> shelterList;

    public ShelterList(Activity context, List<Shelter> shelterList) {
        super(context, R.layout.list_layout, shelterList);
        this.context = context;
        this.shelterList = shelterList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Shelter shelter2 = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNameShelterView);

        Shelter shelter = shelterList.get(position);

        textViewName.setText(shelter.getShelterName());

        return listViewItem;
    }
}
