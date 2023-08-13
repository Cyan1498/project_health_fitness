package com.grupoc.project_health_fitness.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grupoc.project_health_fitness.model.Recordatorio
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.grupoc.project_health_fitness.model.Units

class RecordViewModel : ViewModel() {

    // Agregar nuevas variables LiveData para eventos y datos relacionados con los recordatorios
    private val _recordatorioList = MutableLiveData<List<Recordatorio>>()
    val recordatorioList: LiveData<List<Recordatorio>> get() = _recordatorioList

    private val _signupSuccess = MutableLiveData<Boolean>()
    val signupSuccess: LiveData<Boolean> get() = _signupSuccess

    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> get() = _updateSuccess

    private val _recordatorioDeleted = MutableLiveData<Boolean>()
    val recordatorioDeleted: LiveData<Boolean> get() = _recordatorioDeleted

    //Para algun error
    private val _unknownErrorEvent = MutableLiveData<Boolean>()
    val unknownErrorEvent: LiveData<Boolean> get() = _unknownErrorEvent
    //Units
    // LiveData para las unidades
    private val _unitNames = MutableLiveData<List<String>>()
    val unitNames: LiveData<List<String>> = _unitNames

    private val _unitIds = MutableLiveData<List<String>>()
    val unitIds: LiveData<List<String>> = _unitIds

    private val firestore = FirebaseFirestore.getInstance()
    private val recordatoriosCollection = firestore.collection("recordatorios")

    // Método para crear un nuevo recordatorio
    fun registerRecordatorio(name: String, dateInit: String, instruction: String, unit: String, numDias: Int, note: String) {
        //obtener un ID único que Firestore generaría para un documento que aún no ha sido creado en la colección.
        val id = recordatoriosCollection.document().id
        val recordatorio = Recordatorio(id = id, name = name, dateInit = dateInit, instruction = instruction, unit = unit, numDias = numDias, note = note, createdAt = Timestamp.now())
        addRecordatorioToFirestore(recordatorio)
    }
    private fun addRecordatorioToFirestore(recordatorio: Recordatorio) {
        recordatoriosCollection.document(recordatorio.id!!).set(recordatorio)
            .addOnSuccessListener {
                _signupSuccess.value = true
            }
            .addOnFailureListener {
                _unknownErrorEvent.value = true
            }
    }

    // Método para actualizar un recordatorio existente
    fun updateRecordatorio(recordatorioId: String, name: String, dateInit: String, instruction: String, unit: String, numDias: Int, note: String) {
        val updatedRecordatorio = Recordatorio(recordatorioId, name, dateInit, instruction, unit, numDias, note, createdAt = Timestamp.now())
        recordatoriosCollection.document(recordatorioId)
            .set(updatedRecordatorio)
            .addOnSuccessListener {
                _updateSuccess.value = true
            }
            .addOnFailureListener {
                // Handle failure
                _updateSuccess.value = true
                // You can set an error message or handle it as per your app's requirement
            }
    }

    // Método para eliminar un recordatorio
    fun deleteRecordatorio(recordatorio: Recordatorio) {
        recordatoriosCollection.document(recordatorio.id!!)
            .delete()
            .addOnSuccessListener {
                _recordatorioDeleted.value = true
            }
            .addOnFailureListener {
                // Handle failure
                // You can set an error message or handle it as per your app's requirement
            }
    }

    // Método para obtener la lista de recordatorios desde Firestore
    fun getAllRecordatorios(): LiveData<List<Recordatorio>> {
        recordatoriosCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val recordatorios = mutableListOf<Recordatorio>()
                for (document in querySnapshot) {
                    val recordatorio = document.toObject(Recordatorio::class.java)
                    recordatorios.add(recordatorio)
                }
                _recordatorioList.value = recordatorios
            }
            .addOnFailureListener { exception ->
                // Handle failure
                // You can set an error message or handle it as per your app's requirement
            }
        return recordatorioList
    }

    // Método para obtener la lista de unidades desde Firestore
    fun getUnits(): LiveData<List<String>> {
        val unitsLiveData = MutableLiveData<List<String>>()
        val unitsCollection = firestore.collection("units")

        unitsCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val units = mutableListOf<String>()
                for (document in querySnapshot) {
                    val unit = document.getString("name")
                    if (unit != null) {
                        units.add(unit)
                    }
                }
                unitsLiveData.value = units
            }
            .addOnFailureListener { exception ->
            }

        return unitsLiveData
    }
    // Método para obtener las unidades desde Firestore
//    fun getUnits() {
//        val unitsCollection = firestore.collection("units")
//
//        unitsCollection.get()
//            .addOnSuccessListener { querySnapshot ->
//                val unitNames = mutableListOf<String>()
//                val unitIds = mutableListOf<String>()
//
//                for (document in querySnapshot) {
//                    val unit = document.toObject(Units::class.java)
//                    unitNames.add(unit.name)
//                    unitIds.add(document.id)
//                }
//                _unitNames.value = unitNames
//                _unitIds.value = unitIds
//            }
//            .addOnFailureListener { exception ->
//                // Handle failure
//            }
//    }



}
