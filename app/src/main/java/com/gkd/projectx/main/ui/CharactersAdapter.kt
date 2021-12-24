package com.gkd.projectx.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.gkd.domain.entities.Persona
import com.gkd.projectx.R


class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {
    private var charactersList: List<Persona> = ArrayList()

    class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewCharacterImage: ImageView =
            itemView.findViewById(R.id.item_character_image)
        private val textViewCharacterName: TextView =
            itemView.findViewById(R.id.item_character_name)

        fun bindCharacters(character: Persona) {
            with(character) {
                textViewCharacterName.text = name
                imageViewCharacterImage.load(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharactersViewHolder(itemView)
    }

    override fun getItemCount(): Int = charactersList.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bindCharacters(charactersList[position])
    }

    fun updateList(characterList: List<Persona>) {
        charactersList = characterList
        notifyDataSetChanged()
    }
}