/* Copyright (c) 2012, BuildmLearn Contributors listed at http://buildmlearn.org/people/
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

 * Neither the name of the BuildmLearn nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.buildmlearn.comprehensiontemplate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ComprehensionActivity extends ActionBarActivity {

		private TextView timeLeft;
		private Timer timer = new Timer();
		private int timeInSecond;
		private LinearLayout pauseScreenContainer;
		private TextView pauseMessage;
		private Button resumeProceedButton;
		private ScrollView scrollView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.comprehension_view);
		  GlobalData gd = GlobalData.getInstance();
		  timeInSecond = Integer.parseInt(gd.iTime)*60;
			timeLeft = (TextView) findViewById(R.id.time_left);
			scheduleTimer();
			TextView passageTitle = (TextView) findViewById(R.id.passage_title);
			passageTitle.setText(gd.iPassageTitle);
			TextView passageContent = (TextView) findViewById(R.id.passage_content);
			passageContent.setText(gd.iPassage);
			Button doneButton = (Button) findViewById(R.id.done_button);
			doneButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent myIntent = new Intent(arg0.getContext(),
						QuestionActivity.class);
					startActivity(myIntent);
					finish();
				}
			});

			Button pauseButton = (Button) findViewById(R.id.pause_button);
			pauseButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					showPausedState(true);
				}
			});

			pauseScreenContainer = (LinearLayout) findViewById(R.id.pause_screen_container);
			resumeProceedButton = (Button) findViewById(R.id.resume_proceed_button);
			pauseMessage = (TextView) findViewById(R.id.pause_msg);
			scrollView = (ScrollView) findViewById(R.id.scroll_view);
	}

	private void showPausedState(boolean isPaused) {
		if(isPaused) {
			timer.cancel();
			//HIDE the screen
			pauseScreenContainer.setVisibility(View.VISIBLE);
			scrollView.setVisibility(View.GONE);
			if(timeInSecond>0) {
				pauseMessage.setText("Timer paused");
				resumeProceedButton.setText("Resume");
				resumeProceedButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						showPausedState(false);
					}
				});
			} else { //time is finished
				pauseMessage.setText("Time is up");
				resumeProceedButton.setText("Start Quiz");
				resumeProceedButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(ComprehensionActivity.this,
							QuestionActivity.class);
						startActivity(myIntent);
						finish();
					}
				});
			}
		} else {
			timer = new Timer();
			scheduleTimer();
			//SHOW the screen
			pauseScreenContainer.setVisibility(View.GONE);
			scrollView.setVisibility(View.VISIBLE);
		}
	}

	private void scheduleTimer() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (timeInSecond > 0) {
							String displayTime = timeInSecond / 60 + " minutes and " + timeInSecond % 60 + " seconds left.";
							timeLeft.setText(displayTime);
							timeInSecond--;
						} else {
							showPausedState(true);
						}
					}
				});
			}
		},1000, 1000);// 1000 milliseconds
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_info) {

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					ComprehensionActivity.this);

			// set title
			alertDialogBuilder.setTitle("About Us");

			// set dialog message
			alertDialogBuilder
					.setMessage(getString(R.string.about_us))
					.setCancelable(false)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									dialog.dismiss();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();
			TextView msg = (TextView) alertDialog
					.findViewById(android.R.id.message);
			Linkify.addLinks(msg, Linkify.WEB_URLS);

			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}