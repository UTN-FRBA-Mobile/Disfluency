package com.disfluency.components.inputs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.disfluency.R
import java.time.DayOfWeek
import java.util.*

/*
@Preview
@Composable
fun Preview(){
    val state = remember { mutableStateOf("") }
    DummyDaysOfWeekCheckbox("Elegir", state = state)
    Button(onClick = { print("\n\n\"$state\"\n\n\n") }) {}
}*/

@Composable
fun DummyDaysOfWeekCheckbox(label: String, state: MutableState<List<DayOfWeek>>){
    Text(text = label, color = MaterialTheme.colorScheme.primary)
    DaysOfWeekCheckbox{
        state.value = it
    }
}

@Composable
fun DaysOfWeekCheckbox(onChange: (List<DayOfWeek>)->Unit) {
    //Uso '=' en lugar de 'by' para poder settear el value sin conocer la variable.
    val mondaysChecked = remember {mutableStateOf(false)}
    val tuesdaysChecked = remember {mutableStateOf(false)}
    val wednesdaysChecked = remember {mutableStateOf(false)}
    val thursdaysChecked = remember {mutableStateOf(false)}
    val fridaysChecked = remember {mutableStateOf(false)}

    val weekChecks = arrayOf(mondaysChecked, tuesdaysChecked, wednesdaysChecked, thursdaysChecked, fridaysChecked)

    val allDaysChecked = remember(*weekChecks.map {it.value}.toTypedArray()) {
        if      (weekChecks.all { it.value })ToggleableState.On
        else if (weekChecks.all {!it.value}) ToggleableState.Off
        else ToggleableState.Indeterminate
    }

    val daysOfWeekAsEnum = DayOfWeek.values()
    val daysOfWeek = daysOfWeekAsEnum.map { it.name }

    val notifyChange = {
        val checksAsStringList = LinkedList<DayOfWeek>()
        weekChecks.forEachIndexed{
            index, isChecked -> if(isChecked.value) checksAsStringList.add(daysOfWeekAsEnum[index])
        }

        onChange(checksAsStringList)
    }

    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.width(275.dp)) {
        CheckboxAllOptions(allDaysChecked, stringResource(R.string.all_days), weekChecks, notifyChange)

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

        Column(Modifier.fillMaxWidth()){
            for(i in (weekChecks.indices)){
                checkboxForDay(i, weekChecks[i])
            }
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