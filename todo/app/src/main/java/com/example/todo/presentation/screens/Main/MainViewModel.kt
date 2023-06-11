package com.example.todo.presentation.screens.Main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.local.model.Day
import com.example.todo.data.local.model.ToDo
import com.example.todo.domain.usecase.ChangeToDosUseCase
import com.example.todo.domain.usecase.GetAllDayUseCase
import com.example.todo.domain.usecase.GetCurrentDateUseCase
import com.example.todo.domain.usecase.GetDayByDateUseCase
import com.example.todo.domain.usecase.InsertDayUseCase
import com.example.todo.presentation.ui.theme.ColorTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDayByDateUseCase: GetDayByDateUseCase,
    private val insertDayUseCase: InsertDayUseCase,
    private val changeToDosUseCase: ChangeToDosUseCase,
    private val getAllDayUseCase: GetAllDayUseCase
) : ViewModel() {
    private val currentDate = GetCurrentDateUseCase().invoke()
    var isAdd by mutableStateOf(false)
    var dayState = MutableStateFlow(Day())
    private val _toDos = MutableLiveData<List<ToDo>>()
    val toDos: LiveData<List<ToDo>>
        get() = _toDos


    fun initDay() {
        viewModelScope.launch {
            getDayByDateUseCase.invoke(date = currentDate). let {
                try {
                    if (it == null) {
                        val day = Day(
                        motivation = motivationList[(motivationList.indices).random()],
                            date = currentDate
                        )
                        dayState.emit(day)
                        Log.d("11", "new day ннн")
                        insertDayUseCase.invoke(day).let {
                            _toDos.postValue(day.toDos)
                            dayState.emit(day)
                        }
                    } else {
                        dayState.emit(it)
                        _toDos.postValue(it.toDos)
                    }
                } catch (e: Exception) {
                    Log.d("11", e.message.toString())
                }

            }
        }
    }

    fun deleteToDo(toDo: ToDo) {
        viewModelScope.launch {
            Log.d("11", "удалено")
            val newToDos = _toDos.value?.filter { it != toDo }
            newToDos?.let { changeToDosUseCase.invoke(it, dayState.value.date) }
            _toDos.postValue(newToDos)
        }
    }

    fun addToDo(title: String) {
        viewModelScope.launch {
            getDayByDateUseCase.invoke(currentDate).let {
                val newToDos = it.toDos + ToDo(title = title, date = dayState.value.date)
                changeToDosUseCase.invoke(newToDos, date = currentDate)
                _toDos.postValue(newToDos)
            }
        }
    }

    fun updateToDo(toDo: ToDo) {
        viewModelScope.launch {
            //getDayByDateUseCase.invoke(date = currentDate)
            val newToDos = _toDos.value!!.map {
                if (it.title == toDo.title) {
                    toDo
                } else {
                    it
                }
            }
            changeToDosUseCase.invoke(newToDos, currentDate)
            _toDos.postValue(newToDos)
        }
    }

    fun changeIsAdd() {
        isAdd = !isAdd
    }


    private val motivationList = listOf(
        "Поверь в себя и во все, что ты есть. Знай, \nчто внутри тебя есть что-то больше \nлюбого препятствия.",
        "Когда покажется, что цель недостижима – не изменяй цель – измени свой план действий.",
        "Путешествуй не для того, чтобы сбежать от жизни, а для того, чтобы жизнь не сбежала от тебя.",
        "Одно доброе слово может греть три зимних месяца.",
        "Все хотят жить на вершине горы, но вся радость и рост приходят, когда ты взбираешься на нее.",
        "Иногда самые маленькие вещи занимают больше всего места в сердце.",
        "Продолжай двигаться вперед! Никто не добирается куда-то, просто стоя на месте.",
        "Относись к себе с огромной любовью и уважением.",
        "Любить себя – это не значит обделить своей любовью других. Это значит поделиться любовью с собой.",
        "Жизнь проходит так, как ты ее проводишь.",
        "Неудача – это просто возможность начать снова, но уже более мудро.",
        "Черная полоса бывает взлетной!",
        "Верь в себя, и у тебя все получится!",
        "Никогда не поздно быть тем, кем тебе хочется быть.",
        "Не плыви по течению. Не плыви против течения. Плыви туда, куда тебе надо!",
        "Самая тяжелая часть работы — решиться приступить к ней.",
        "Любое препятствие преодолевается настойчивостью.",
        "Работа над собой меняет отношения, меняет судьбу, меняет жизнь.",
        "Всё когда-нибудь заканчивается. Важно то, что начинается после.",
        "Помогай другим и ты поможешь себе. Все во Вселенной взаимосвязано и все возвращается.",
        "Когда одна дверь закрывается, открывается другая. Но часто мы смотрим так долго на закрытую дверь, что не видим открытой.",
        "Лифт к успеху не работает. Используй ступеньки. Шаг за шагом.",
        "Отправной точкой всех достижений является желание.",
        "Не старайся стать успешным человеком, а пытайся стать ценным человеком.",
        "Лучше жалеть о сделанном, чем о несделанном.",
        "Главное верить в себя. Мнение окружающих меняется ежедневно.",
        "Любое достижение начинается с желания попробовать.",
        "Делай как можно больше ошибок, только помни: не совершай одну и ту же ошибку дважды. И ты будешь расти!",
        "Страх уходит во время движения.",
        "Поверьте, что сможете, и уже полпути пройдено.",
        "Не говори людям, что собираешься делать. Сделай это, шокируй их и переходи к следующему проекту. Шокируй. Наслаждайся.",
        "Пусть тебя не толкают сзади проблемы, а вперед ведут мечты!",
        "Иногда для результатов требуется время.",
        "Обязательно дружи с теми, кто лучше тебя. Будешь мучиться, но расти.",
        "Внимание – это по сути энергия. Куда ты направляешь внимание, туда и течет твоя энергия.",
        "Если твоим вниманием управляет другой, ты – рыба. Если ты сам управляешь своим вниманием, ты – рыбак.",
        "Никогда не отказывайся от цели из-за того, что считаешь ее недостижимой.",
        "Не принимай чужое мнение за факты.",
        "Отличная привычка – подходить к зеркалу и, глядя себе в глаза, говорить все хорошее, что хочется сказать.",
        "Делай то, что не можешь не делать!",
        "Если можешь мечтать об этом, то можешь и сделать это.",
        "Успех - это идти от неудачи к неудаче, не теряя энтузиазма.",
        "Не жди. Время никогда не будет подходящим.",
        "Мотивация - это то, что заставляет начать. Привычка - это то, что заставляет продолжать.",
        "Победа - это еще не все, главное это постоянное желание побеждать.",
        "Ограничения живут только в нашем сознании. Но если мы используем свое воображение, наши возможности станут безграничными.",
        "Если не можешь делать великие дела, делай маленькие дела по-великому.",
        "С новым днем приходит новая сила и новые мысли.",
        "Не смотри на часы. Делай то, что они делают. Не останавливайся.",
        "Чтобы пересечь океан, нужно набраться смелости потерять из виду берег.",
        "Самый верный путь к успеху - это попробовать еще раз.",
        "Каждый день делай одно дело, которого боишься.",
        "Если ветер не помогает, берись за весла.",)

}