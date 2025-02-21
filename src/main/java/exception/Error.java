package exception;

public record Error(
        String message,
        String details,
        String status
){
}