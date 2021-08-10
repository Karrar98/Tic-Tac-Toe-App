package com.example.tictactoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tictactoeapp.databinding.ActivityMainBinding
import com.google.android.material.imageview.ShapeableImageView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var board = arrayListOf<String>("", "", "", "", "", "", "", "", "")
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnReset.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity::class.java))
        }
    }

    fun onclickPosition(v : View){
        when((v as ShapeableImageView)){
            binding.positionOne -> selectPosition(v, 0)
            binding.positionTwo -> selectPosition(v, 1)
            binding.positionThree -> selectPosition(v, 2)
            binding.positionFour -> selectPosition(v, 3)
            binding.positionFive -> selectPosition(v, 4)
            binding.positionSix -> selectPosition(v, 5)
            binding.positionSeven -> selectPosition(v, 6)
            binding.positionEight -> selectPosition(v, 7)
            binding.positionNine -> selectPosition(v, 8)
        }
    }

    private fun selectPosition(v : View, positionIndex : Int){
        if (board[positionIndex] == "") {
            (v as ShapeableImageView).setImageResource(R.drawable.ic_x)
            board[positionIndex] = "X"
            if(!isBoardFull(board) && !result(board, "X")) {
                val position = getComputerMove(board)
                board[position] = "O"
                displayComputerSelect(position)
            }
        }
        resultOut(board)
    }

    private fun getComputerMove(board: ArrayList<String>): Int {

        //check if computer can win in this move
        for (i in 0 until board.count()){
            var copy: ArrayList<String> = getBoardCopy(board)
            if(copy[i] == "") copy[i] = "O"
            if(result(copy, "O")) return i
        }

        //check if player could win in the next move
        for (i in 0 until board.count()){
            var copy2 = getBoardCopy(board)
            if(copy2[i] == "") copy2[i] = "X"
            if(result(copy2, "X")) return i
        }

        //try to take corners if its free
        var move = choseRandomMove(board, arrayListOf<Int>(0, 2, 6, 8))
        if(move != -1)
            return move

        //try to take center if its free
        if(board[4] == "") return 4

        //move on one of the sides
        return choseRandomMove(board, arrayListOf<Int>(1, 3, 5, 7))
    }


    private fun choseRandomMove(board: ArrayList<String>, list: ArrayList<Int>): Int {
        var possibleMoves = arrayListOf<Int>()
        for (i in list){
            if(board[i] == "") possibleMoves.add(i)
        }
        return if(possibleMoves.isEmpty()) -1
        else {
            var index = Random().nextInt(possibleMoves.count())
            possibleMoves[index]
        }
    }

    private fun getBoardCopy(board: ArrayList<String>): ArrayList<String> {
        var bd = arrayListOf<String>("", "", "", "", "", "", "", "", "")
        for (i in 0 until board.count()) {
            bd[i] = board[i]
        }
        return bd
    }

    private fun isBoardFull(board: ArrayList<String>): Boolean {
        for (i in board)
            if(i != "X" && i != "O") return false
        return true
    }

    private fun displayComputerSelect(position: Int){
        if(position == 0) binding.positionOne.setImageResource(R.drawable.ic_o)
        else if(position == 1) binding.positionTwo.setImageResource(R.drawable.ic_o)
        else if(position == 2) binding.positionThree.setImageResource(R.drawable.ic_o)
        else if(position == 3) binding.positionFour.setImageResource(R.drawable.ic_o)
        else if(position == 4) binding.positionFive.setImageResource(R.drawable.ic_o)
        else if(position == 5) binding.positionSix.setImageResource(R.drawable.ic_o)
        else if(position == 6) binding.positionSeven.setImageResource(R.drawable.ic_o)
        else if(position == 7) binding.positionEight.setImageResource(R.drawable.ic_o)
        else if(position == 8) binding.positionNine.setImageResource(R.drawable.ic_o)
    }


    private fun resultOut(board: ArrayList<String>){
        if(result(board, "X")){
            startActivity(Intent(this@MainActivity, WonActivity::class.java).putExtra("player", "YOU"))
        }else if(result(board, "O")){
            startActivity(Intent(this@MainActivity, WonActivity::class.java).putExtra("player", "COMPUTER"))
        }
        if(isBoardFull(board)){
            startActivity(Intent(this@MainActivity, WonActivity::class.java).putExtra("player", "Tie"))
        }
    }

    private fun result(bd: ArrayList<String>, s: String): Boolean =
        if(bd[0] == s && bd[1] == s && bd[2] == s) true
        else if(bd[3] == s && bd[4] == s && bd[5] == s) true
        else if(bd[6] == s && bd[7] == s && bd[8] == s) true
        else if(bd[0] == s && bd[3] == s && bd[6] == s) true
        else if(bd[1] == s && bd[4] == s && bd[7] == s) true
        else if(bd[2] == s && bd[5] == s && bd[8] == s) true
        else if(bd[0] == s && bd[4] == s && bd[8] == s) true
        else bd[2] == s && bd[4] == s && bd[6] == s

    override fun onPause() {
        super.onPause()
        finish()
    }
}

