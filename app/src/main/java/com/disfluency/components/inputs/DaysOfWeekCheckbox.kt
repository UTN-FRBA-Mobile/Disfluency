package com.disfluency.components.inputs

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

@Preview
@Composable
fun Preview(){
    val state = remember { mutableStateOf("") }
    DummyDaysOfWeekCheckbox("Elegir", state = state)
    Button(onClick = { print("\n\n\"$state\"\n\n\n") }) {}
}

@Composable
fun DummyDaysOfWeekCheckbox(label: String, state: MutableState<String>){
    Text(text = label, color = MaterialTheme.colorScheme.primary)
    DaysOfWeekCheckbox{
        state.value = if(it.size>1) {
            "${it.dropLast(1).joinToString(", ")} y ${it.last()}"
        } else it.joinToString()
    }
}

@Composable
fun DaysOfWeekCheckbox(onChange: (List<String>)->Unit) {
    //Uso '=' en lugar de 'by' para poder settear el value sin conocer la variable.
    val mondaysChecked = remember {mutableStateOf(false)}
    val tuesdaysChecked = remember {mutableStateOf(false)}
    val wednesdaysChecked = remember {mutableStateOf(false)}
    val thursdaysChecked = remember {mutableStateOf(false)}
    val fridaysChecked = remember {mutableStateOf(false)}
    val sathurdaysChecked = remember {mutableStateOf(false)}
    val sundaysChecked = remember {mutableStateOf(false)}

    val weekChecks = arrayOf(mondaysChecked, tuesdaysChecked, wednesdaysChecked, thursdaysChecked, fridaysChecked, sathurdaysChecked, sundaysChecked)

    val allDaysChecked = remember(*weekChecks.map {it.value}.toTypedArray()) {
        if      (weekChecks.all { it.value })ToggleableState.On
        else if (weekChecks.all {!it.value}) ToggleableState.Off
        else ToggleableState.Indeterminate
    }

    val daysOfWeek = arrayOf(
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    )

    val notifyChange = {
        val checksAsStringList = LinkedList<String>()
        weekChecks.forEachIndexed{
            index, isChecked -> if(isChecked.value) checksAsStringList.add(daysOfWeek[index])
        }

        onChange(checksAsStringList)
    }

    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.width(275.dp)) {
        CheckboxAllOptions(allDaysChecked, "Todos los días", weekChecks, notifyChange)

        val checkboxForDay = @Composable{
            index: Int, checkbox: MutableState<Boolean> ->
                CheckBoxItem(
                    checked = checkbox,
                    dayName = daysOfWeek[index],
                    onCheckedChange = {
                        checkbox.value = it
                        notifyChange()
                    }
                )
        }

        for(i in (weekChecks.indices)){
            checkboxForDay(i, weekChecks[i])
        }
    }
}

@Composable
private fun CheckboxAllOptions(state: ToggleableState, label: String, subCheckboxes: Array<MutableState<Boolean>>, onChange: () -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically) {
        TriStateCheckbox(
            state = state,
            onClick = {
                val isChecked = state != ToggleableState.On

                for (weekCheck in subCheckboxes) {
                    weekCheck.value = isChecked
                }

                onChange()
            }
        )
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = label
        )
    }
}

@Composable
private fun CheckBoxItem(
    checked: MutableState<Boolean>,
    dayName: String,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked.value, onCheckedChange)
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = dayName
        )
    }
}