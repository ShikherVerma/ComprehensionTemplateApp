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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TFTComprehensionActivity extends ActionBarActivity {
	private GlobalData gd;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.start_view);

		gd = GlobalData.getInstance();
		reInitialize();

		gd.readXml(TFTComprehensionActivity.this, "comprehension_content.xml");
		TextView Author = (TextView) findViewById(R.id.tv_author);
		TextView Title = (TextView) findViewById(R.id.tv_apptitle);
		TextView PassageTitle = (TextView) findViewById(R.id.tv_passage_title);
		TextView Time = (TextView) findViewById(R.id.tv_time);
		Author.setText(gd.iAuthor);
		Title.setText(gd.iTitle);
		PassageTitle.setText(gd.iPassageTitle);
		Time.setText(gd.iTime + "minutes");

		Button startButton = (Button) findViewById(R.id.btn_start);
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(arg0.getContext(),
						ComprehensionActivity.class);
				startActivity(myIntent);
				finish();
			}
		});
	}

	private void reInitialize() {
		gd.total = 0;
		gd.correct = 0;
		gd.wrong = 0;
		gd.iQuizList.clear();
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
					TFTComprehensionActivity.this);

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
