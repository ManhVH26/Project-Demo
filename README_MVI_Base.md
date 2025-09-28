# MVI Base Architecture - Hướng dẫn chi tiết

## Tổng quan

Dự án này sử dụng kiến trúc **MVI (Model-View-Intent)** với các base classes được thiết kế để đảm bảo tính nhất quán và dễ bảo trì trong toàn bộ ứng dụng Android.

## Kiến trúc MVI

MVI là một pattern kiến trúc giúp quản lý state và side effects một cách có tổ chức:

- **Model**: Đại diện cho state của ứng dụng
- **View**: UI layer hiển thị state và gửi intents
- **Intent**: Các hành động mà user có thể thực hiện

## Các thành phần chính

### 1. MviContract.kt

```kotlin
interface MviContract<S : MviContract.State, I : MviContract.Intent, E : MviContract.Effect>
```

**Mục đích**: Định nghĩa contract cho tất cả các ViewModel trong ứng dụng.

#### Các interface con:

- **State**: Đại diện cho trạng thái hiện tại của UI
- **Intent**: Đại diện cho các hành động mà user có thể thực hiện
- **Effect**: Đại diện cho các side effects (navigation, show toast, etc.)

#### Các thuộc tính và phương thức:

- `val state: S`: Trạng thái hiện tại
- `fun processIntent(intent: I)`: Xử lý các intent từ UI

### 2. BaseViewModel.kt

```kotlin
abstract class BaseViewModel<S : MviContract.State, I : MviContract.Intent, E : MviContract.Effect>
```

**Mục đích**: Base class cung cấp implementation chung cho tất cả ViewModel.

#### Các thành phần chính:

##### State Management
```kotlin
private val _uiState = MutableStateFlow(initialState)
val uiState: StateFlow<S> = _uiState.asStateFlow()
```
- Sử dụng `StateFlow` để quản lý state
- State là immutable và được observe từ UI

##### Effect Management
```kotlin
private val _effect = MutableSharedFlow<E>()
val effect: SharedFlow<E> = _effect.asSharedFlow()
```
- Sử dụng `SharedFlow` cho one-time effects
- Effects được consume một lần và tự động clear

##### State Updates
```kotlin
protected fun setState(reducer: S.() -> S) {
    val newState = state.reducer()
    _uiState.update { newState }
}
```
- Functional approach để update state
- Đảm bảo immutability

##### Effect Emission
```kotlin
protected fun setEffect(effectProducer: suspend () -> E) {
    viewModelScope.launch {
        _effect.emit(effectProducer())
    }
}
```
- Emit effects trong coroutine scope
- Tự động cleanup khi ViewModel bị destroy

## Cách sử dụng

### 1. Tạo State

```kotlin
data class HomeState(
    val isLoading: Boolean = false,
    val data: List<String> = emptyList(),
    val error: String? = null
) : MviContract.State
```

### 2. Tạo Intent

```kotlin
sealed class HomeIntent : MviContract.Intent {
    object LoadData : HomeIntent()
    object RefreshData : HomeIntent()
    data class ItemClicked(val item: String) : HomeIntent()
}
```

### 3. Tạo Effect

```kotlin
sealed class HomeEffect : MviContract.Effect {
    data class ShowToast(val message: String) : HomeEffect()
    object NavigateToDetail : HomeEffect()
    data class ShowError(val error: String) : HomeEffect()
}
```

### 4. Implement ViewModel

```kotlin
class HomeViewModel : BaseViewModel<HomeState, HomeIntent, HomeEffect>(
    initialState = HomeState()
) {
    
    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> loadData()
            is HomeIntent.RefreshData -> refreshData()
            is HomeIntent.ItemClicked -> handleItemClick(intent.item)
        }
    }
    
    private fun loadData() {
        setState { copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            try {
                val data = repository.getData()
                setState { copy(isLoading = false, data = data) }
            } catch (e: Exception) {
                setState { copy(isLoading = false, error = e.message) }
                setEffect { HomeEffect.ShowError(e.message ?: "Unknown error") }
            }
        }
    }
    
    private fun handleItemClick(item: String) {
        setEffect { HomeEffect.NavigateToDetail }
    }
}
```

### 5. Sử dụng trong Fragment/Activity

```kotlin
class HomeFragment : Fragment() {
    
    private val viewModel: HomeViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Observe state
        viewModel.uiState.collectLatest { state ->
            when {
                state.isLoading -> showLoading()
                state.error != null -> showError(state.error)
                else -> showData(state.data)
            }
        }
        
        // Observe effects
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is HomeEffect.ShowToast -> showToast(effect.message)
                is HomeEffect.NavigateToDetail -> navigateToDetail()
                is HomeEffect.ShowError -> showErrorDialog(effect.error)
            }
        }
        
        // Send intents
        binding.refreshButton.setOnClickListener {
            viewModel.processIntent(HomeIntent.RefreshData)
        }
    }
}
```

## Lợi ích của kiến trúc này

### 1. **Predictable State Management**
- State luôn được quản lý tập trung
- Không có side effects không mong muốn
- Dễ debug và test

### 2. **Unidirectional Data Flow**
- Data chỉ flow một chiều: Intent → ViewModel → State → UI
- Dễ theo dõi và hiểu logic

### 3. **Separation of Concerns**
- UI chỉ quan tâm đến hiển thị
- Business logic được tách biệt trong ViewModel
- Effects được xử lý riêng biệt

### 4. **Testability**
- Dễ test ViewModel với các state khác nhau
- Mock intents và verify state changes
- Test effects một cách độc lập

### 5. **Type Safety**
- Compile-time checking cho state, intents, effects
- Giảm runtime errors

## Best Practices

### 1. **State Design**
- State nên là immutable
- Sử dụng `data class` với `copy()` method
- Tránh nested mutable objects

### 2. **Intent Design**
- Sử dụng `sealed class` cho type safety
- Mỗi intent nên đại diện cho một hành động cụ thể
- Tránh intents quá phức tạp

### 3. **Effect Design**
- Effects nên là one-time events
- Sử dụng `SharedFlow` thay vì `StateFlow` cho effects
- Effects không nên chứa business logic

### 4. **ViewModel Implementation**
- Luôn xử lý exceptions
- Sử dụng `suspend` functions cho async operations
- Clean up resources trong `onCleared()`

## Kết luận

Kiến trúc MVI base này cung cấp một foundation vững chắc cho việc phát triển ứng dụng Android. Nó đảm bảo:

- Code dễ đọc và maintain
- State management nhất quán
- Dễ test và debug
- Type safety
- Separation of concerns

Việc sử dụng đúng pattern này sẽ giúp team phát triển ứng dụng một cách hiệu quả và giảm thiểu bugs.
