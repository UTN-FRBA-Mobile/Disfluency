package com.disfluency.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun DaysOfWeekCheckbox() {
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
        if (weekChecks.all { it.value })  ToggleableState.On
        else if (weekChecks.all {!it.value}) ToggleableState.Off
        else ToggleableState.Indeterminate
    }

    Column(horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TriStateCheckbox(
                state = allDaysChecked,
                onClick = {
                    val state = allDaysChecked != ToggleableState.On

                    for (weekCheck in weekChecks) {
                        weekCheck.value = state
                    }
                }
            )
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = "Todos los días"
            )
        }

        val daysOfWeek = arrayOf(
            "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
        )

        val checkboxForDay = @Composable{
                index: Int, checkbox: MutableState<Boolean> ->
            CheckBoxItem(
                checked = checkbox,
                dayName = daysOfWeek[index],
                onCheckedChange = { checkbox.value = it }
            )
        }

        LazyColumn{
            items(weekChecks.size){
                checkboxForDay(it, weekChecks[it])
            }
        }
    }
}

@Composable
private fun CheckBoxItem(
    checked: MutableState<Boolean>,
    dayName: String,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked.value, onCheckedChange)
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = dayName
        )
    }
}