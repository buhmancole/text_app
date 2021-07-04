package com.colebuhmanhw3.watchapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colebuhmanhw3.api.MessageAdapter;
import com.colebuhmanhw3.api.viewmodels.UserViewModel;

import java.util.Locale;

public class ConversationFragment extends Fragment {
    private boolean isTalking = false;

    public ConversationFragment() {
        super(R.layout.fragment_conversation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserViewModel viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        String name = ((MainActivity)getActivity()).conversation;
        RecyclerView mList = view.findViewById(R.id.message_list);

        MessageAdapter adapter = new MessageAdapter(viewModel.getConversation(name).messages);
        mList.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setStackFromEnd(true);
        mList.setLayoutManager(llm);

        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
        recognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {
                System.out.println("Error: " + error);
            }

            @Override
            public void onResults(Bundle results) {
                String result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                viewModel.addMessage(name, result);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        view.findViewById(R.id.send).setOnClickListener(v -> {
            if(!isTalking) {
                recognizer.startListening(recognizerIntent);
                isTalking = true;
            } else {
                isTalking = false;
                recognizer.stopListening();
            }
        });
    }
}
