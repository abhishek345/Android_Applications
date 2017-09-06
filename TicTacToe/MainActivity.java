package com.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener, OnCheckedChangeListener{

	private String symbol = "0";
	private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, startButton;
	private RadioGroup radioGroup;
	private RadioButton zero, cross;
	private boolean started = false; 
	private int[][] board = {{0,0,0},{0,0,0},{0,0,0}};
	private int totalClicks=0;
	private boolean gameOver = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		b1 = (Button)findViewById(R.id.button1);
		b2 = (Button)findViewById(R.id.button2);
		b3 = (Button)findViewById(R.id.button3);
		b4 = (Button)findViewById(R.id.button4);
		b5 = (Button)findViewById(R.id.button5);
		b6 = (Button)findViewById(R.id.button6);
		b7 = (Button)findViewById(R.id.button7);
		b8 = (Button)findViewById(R.id.button8);
		b9 = (Button)findViewById(R.id.button9);
		startButton = (Button)findViewById(R.id.start);
	
		radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
		zero = (RadioButton)findViewById(R.id.zero);
		cross = (RadioButton)findViewById(R.id.cross);
				
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
		b8.setOnClickListener(this);
		b9.setOnClickListener(this);
		startButton.setOnClickListener(this);
		
		radioGroup.setOnCheckedChangeListener(this);
	}

	
	@Override
	public void onClick(View v) {
		Button clickedButton = (Button)v;
		
		if(clickedButton.getId() == R.id.start)
		{
			startTheGame();
			return;
		}
		
		if(gameOver)
			return;
		
		if(!started)
		{
			started = true;
			zero.setEnabled(false);
			cross.setEnabled(false);
		}
					
		String currentLabel = clickedButton.getText().toString();
		if(currentLabel.isEmpty())
		{
			totalClicks++;
			clickedButton.setText(symbol);
			if(updateBoard(clickedButton))
			{
				gameOver = true;
				return;
			}
			setSymbol();
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(checkedId == R.id.zero)
		{
			symbol = "0";
		}
		else
		symbol = "X";
	}
	
	private void setSymbol()
	{
		if(symbol.equals("0"))
			symbol = "X";
		else
			symbol = "0";
	}
	
	private void startTheGame()
	{
		totalClicks = 0;
		started = false;
		gameOver = false;
		clearButtonsLabel();
		zero.setEnabled(true);
		cross.setEnabled(true);		
		
		zero.setChecked(true);
		symbol = "0";
		
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				board[i][j]=0;
			}
		}	
	}
	
	private void clearButtonsLabel()
	{
		b1.setText(""); 
		b2.setText("");
		b3.setText("");
		b4.setText("");
		b5.setText("");
		b6.setText("");
		b7.setText("");
		b8.setText("");
		b9.setText("");
	}
	
	private boolean updateBoard(Button clickedButton)
	{
		String currentSymbol = clickedButton.getText().toString();
		int value=0, col=-1, row=-1;
		int totalRowwise=0, totalColumnwise=0, totalDiagonalwise1=0, totalDiagonalwise2=0;
		
		
		if(currentSymbol.equals("0"))
			value = -1;
		else
			value = 1;
		
		switch(clickedButton.getId())
		{
			case R.id.button1:
				row=0;
				col=0;
				break;
			
			case R.id.button2:
				row=0;
				col=1;
				break;
			
			case R.id.button3:
				row=0;
				col=2;
				break;
				
			case R.id.button4:
				row=1;
				col=0;
				break;
				
			case R.id.button5:
				row=1;
				col=1;
				break;
				
			case R.id.button6:
				row=1;
				col=2;
				break;
				
			case R.id.button7:
				row=2;
				col=0;
				break;
				
			case R.id.button8:
				row=2;
				col=1;
				break;
				
			case R.id.button9:
				row=2;
				col=2;
				break;
				
			default:
				break;			
		}
		
		board[row][col] = value;
		
		totalRowwise = board[row][0] + board[row][1] + board[row][2];
		totalColumnwise = board[0][col] + board[1][col] + board[2][col];
		totalDiagonalwise1 =  board[0][0] + board[1][1] + board[2][2];
		totalDiagonalwise2 =  board[2][0] + board[1][1] + board[0][2];
		
		if(value == 1)
		{
			if(totalRowwise == 3 || totalColumnwise == 3 || totalDiagonalwise1 == 3 || totalDiagonalwise2 == 3)
			{
				//win for X
				Toast.makeText(getApplicationContext(), "Win for X",Toast.LENGTH_LONG).show();
				return true;
			}
		}
		else if(value == -1)
		{
			if(totalRowwise == -3 || totalColumnwise == -3 || totalDiagonalwise1 == -3 || totalDiagonalwise2 == -3)
			{
				//win for O
				Toast.makeText(getApplicationContext(), "Win for O",Toast.LENGTH_LONG).show();
				return true;
			}
		}
		
		if(totalClicks==9)
		{
			Toast.makeText(getApplicationContext(), "Game Drawn",Toast.LENGTH_LONG).show();
			return true;
		}
		
		return false;
	}
}

