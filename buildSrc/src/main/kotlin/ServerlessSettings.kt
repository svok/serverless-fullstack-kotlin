import org.gradle.api.internal.TaskInputsInternal
import org.gradle.api.internal.TaskOutputsInternal

class ServerlessSettings(
    val inputs: TaskInputsInternal,
    val outputs: TaskOutputsInternal,
    var args: Array<String>
)
