package com.example.flightreservationsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import model.flight_model;

public class adapter_class extends RecyclerView.Adapter<adapter_class.ViewHolder> {

    ArrayList<flight_model> list;

    public adapter_class(ArrayList<flight_model> list){

        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.flight_id.setText("Flight Id: " + list.get(position).getFlight_id());
        holder.flight_name.setText("Flight Name: " +  list.get(position).getFlight_name());
        holder.arrival.setText("Arrival: " + list.get(position).getArrival());
        holder.departure.setText("Departure: " + list.get(position).getDeparture());
        holder.price.setText("Price: " + list.get(position).getFlight_price());
        holder.flight_takeoff_date.setText("Take Off Date: " + list.get(position).getTakeoff_date());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView flight_name,flight_id,arrival,departure,price,flight_takeoff_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flight_name = itemView.findViewById(R.id.holder_flight_name);
            flight_id = itemView.findViewById(R.id.holder_flight_id);
            arrival = itemView.findViewById(R.id.holder_arrival);
            departure = itemView.findViewById(R.id.holder_departure);
            price = itemView.findViewById(R.id.holder_price);
            flight_takeoff_date = itemView.findViewById(R.id.holder_takeoffdate);
        }
    }
}
