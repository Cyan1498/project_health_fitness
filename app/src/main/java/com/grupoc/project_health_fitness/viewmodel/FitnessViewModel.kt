package com.grupoc.project_health_fitness.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.grupoc.project_health_fitness.model.Fitness
import com.grupoc.project_health_fitness.model.Recordatorio

class FitnessViewModel: ViewModel() {

    private val _fitnessList = MutableLiveData<List<Fitness>>()
    val fitnessList: LiveData<List<Fitness>> get() = _fitnessList

    private val _fitnessDeleted = MutableLiveData<Boolean>()

    private val _signupSuccess = MutableLiveData<Boolean>()
    val signupSuccess: LiveData<Boolean> get() = _signupSuccess

    private val _unknownErrorEvent = MutableLiveData<Boolean>()

    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> get() = _updateSuccess

    private val firestore = FirebaseFirestore.getInstance()
    private var fitnessCollection = firestore.collection("excercises")

    fun registerFitness(category: String, reps: String, rest: String, series: String) {
        //obtener un ID único que Firestore generaría para un documento que aún no ha sido creado en la colección.
        val id = fitnessCollection.document().id
        val fitness = Fitness(id = id, category = category, reps = reps, rest = rest, series = series)
        addFitnessToFirestore(fitness)
    }

    private fun addFitnessToFirestore(fitness: Fitness) {
        fitnessCollection.document(fitness.id!!).set(fitness)
            .addOnSuccessListener {
                _signupSuccess.value = true
            }
            .addOnFailureListener {
                _unknownErrorEvent.value = true
            }
    }

    fun getAllFitness(): LiveData<List<Fitness>> {
        fitnessCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val fitnessL = mutableListOf<Fitness>()
                for (document in querySnapshot) {
                    val fitness = document.toObject(Fitness::class.java)
                    fitnessL.add(fitness)
                }
                _fitnessList.value = fitnessL
            }
            .addOnFailureListener { exception ->
                // Handle failure
                // You can set an error message or handle it as per your app's requirement
            }
        return fitnessList
    }

    fun updateFitness(fitnessId: String, category: String, reps: String, rest: String, series: String) {
        val updatedFitness = Fitness(fitnessId, category, reps, rest, series)
        fitnessCollection.document(fitnessId)
            .set(updatedFitness)
            .addOnSuccessListener {
                _updateSuccess.value = true
            }
            .addOnFailureListener {
                // Handle failure
                _unknownErrorEvent.value = true
                // You can set an error message or handle it as per your app's requirement
            }
    }

    fun deleteFitness(fitness: Fitness) {
        fitnessCollection.document(fitness.id!!)
            .delete()
            .addOnSuccessListener {
                _fitnessDeleted.value = true
            }
            .addOnFailureListener {
                // Handle failure
                // You can set an error message or handle it as per your app's requirement
                _unknownErrorEvent.value = true
            }
    }
}