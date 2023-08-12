package com.grupoc.project_health_fitness.view.fragments.fitness

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentFitnessBinding
import com.grupoc.project_health_fitness.model.Fitness
import com.grupoc.project_health_fitness.view.fragments.fitness.adapter.FitnessAdapter

class FitnessFragment : Fragment() {

    private var _binding: FragmentFitnessBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerfitness: RecyclerView
    private lateinit var fitnessArrayList: ArrayList<Fitness>
    private lateinit var adapter: FitnessAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var buttonAdd: Button
//    private val fitnessRef = db.collection("excercises")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFitnessBinding.inflate(inflater, container, false)


        binding.addFitness.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                binding.addFitness.isVisible = false
                val navController = findNavController()
                navController.navigate(R.id.action_fitnessFragment_to_addFitnessFragment)
            }
        })

        recyclerfitness = binding.recyclerFitness
        recyclerfitness.layoutManager = LinearLayoutManager(requireActivity())
        recyclerfitness.setHasFixedSize(true)

        fitnessArrayList = arrayListOf()
        adapter = FitnessAdapter(fitnessArrayList)

//        val query = FirebaseFirestore.getInstance().collection("excercises")
//        val fitnessArrayList = FirestoreRecyclerOptions.Builder<Fitness>()
//            .setQuery(query, Fitness::class.java)
//            .build()
//        adapter = FitnessAdapter(fitnessArrayList)

        recyclerfitness.adapter = adapter

        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder.adapterPosition)
//                val position = viewHolder.bindingAdapterPosition
//                val documentSnapshot = adapter.snapshots.getSnapshot(position)
//                adapter.deleteItem(position)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerfitness)

        setupRecyclerFitness()

        return binding.root
    }

    private fun setupRecyclerFitness() {
        db = FirebaseFirestore.getInstance()
        db.collection("excercises").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        fitnessArrayList.add(dc.document.toObject(Fitness::class.java))
                    }
                }
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}