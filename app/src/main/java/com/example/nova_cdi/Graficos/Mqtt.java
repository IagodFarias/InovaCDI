package com.example.nova_cdi.Graficos;

import static java.nio.charset.StandardCharsets.UTF_8;

import android.annotation.SuppressLint;
import android.util.Log;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import java.util.function.Consumer;


public class Mqtt {

    private final String server = "broker.hivemq.com";
    private final int port = 1883;

    private final String data_seed = "cdi/inova/dados";
    private final String all_seed = "cdi/inova/comandos";
    private boolean first_acess;

    private final Mqtt5AsyncClient client =  MqttClient.builder()
            .useMqttVersion5()
            .serverHost(server)
            .serverPort(port)
            .buildAsync();


    public Mqtt(boolean inHome){
        if(inHome){
            first_acess = false;
        }else{
            first_acess = true;
        }
    }

    public Mqtt(){
        this(false);
    }


    @SuppressLint("CheckResult")
    private void connected(Mqtt5ConnAck mqtt5ConnAck, Throwable throwable, Consumer<Mqtt5Publish> callback) {
        if(throwable != null) {
            Log.e("Mqtt.java", "Falha: " + throwable.getMessage());
        } else {
            Log.d("Mqtt.java", "Sucesso");


            client.subscribeWith()
                    .topicFilter(data_seed)
                    .callback(callback)
                    .send()
                    .whenComplete((subAck, subThrowable) -> {
                        if (subThrowable != null) {
                            Log.e("Mqtt.java", "Falha ao subscrever", subThrowable);
                        } else {
                            if(first_acess){
                                Log.d("Mqtt.java", "Primeiro acesso, a pedir todos os dados...");
                                allFlag();
                                first_acess = false;
                            }
                        }
                    });

        }
    }

    public void get_last(Consumer<Mqtt5Publish> callback) {

        client.connect()
                .whenComplete(((mqtt5ConnAck, throwable) -> {
                    connected(mqtt5ConnAck, throwable, callback);
                } ));
    }

    public void allFlag(){
        if(client.getState().isConnected()) {
            client.publishWith()
                    .topic(all_seed)
                    .payload(UTF_8.encode("ALL"))
                    .send();
        } else {
            Log.w("Mqtt.java", "Não foi possível enviar 'ALL', cliente não está ligado.");
        }
    }

    public void disconnect() {

        if(client.getState().isConnected()){
            client.disconnect();
            Log.d("Mqtt.java", "Desligado com sucesso.");
        }

    }
}
