package com.example.flightreservationsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import model.flight_model;

public class adapter_book_class extends  RecyclerView.Adapter<adapter_book_class.ViewHolder>{

    ArrayList<flight_model> list;

    public adapter_book_class(ArrayList<flight_model> list){
        this.list = list;
    }

    @NonNull
    @Override
    public adapter_book_class.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_bookflight, parent, false);
        return new adapter_book_class.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_book_class.ViewHolder holder, int position) {

        holder.flight_id.setText("FLIGHT ID: " + list.get(position).getFlight_id());
        holder.flight_name.setText("FLIGHT NAME: " +  list.get(position).getFlight_name());
        holder.arrival.setText("ARRIVAL: " + list.get(position).getArrival());
        holder.departure.setText("DEPARTURE: " + list.get(position).getDeparture());
        holder.price.setText("PRICE: " + list.get(position).getFlight_price());
        holder.flight_takeoff_date.setText("TAKE OFF DATE: " + list.get(position).getTakeoff_date());
        holder.available_seat.setText("SEATS: " + list.get(position).getAvailable_seat());
        holder.flight_source.setText("SOURCE: " + list.get(position).getFlight_source());
        holder.flight_destination.setText("DESTINATION: " + list.get(position).getFlight_destination());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView flight_name,flight_id,arrival,departure,price,flight_takeoff_date,available_seat,flight_source, flight_destination;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flight_name = itemView.findViewById(R.id.holder_book_flight_name);
            flight_id = itemView.findViewById(R.id.holder_book_flight_id);
            arrival = itemView.findViewById(R.id.holder_book_arrival);
            departure = itemView.findViewById(R.id.holder_book_departure);
            price = itemView.findViewById(R.id.holder_book_price);
            flight_takeoff_date = itemView.findViewById(R.id.holder_book_takeoffdate);
            available_seat = itemView.findViewById(R.id.holder_book_seat);
            flight_source = itemView.findViewById(R.id.holder_book_source);
            flight_destination = itemView.findViewById(R.id.holder_book_destination);
        }
    }
}


