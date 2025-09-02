namespace Jimx.ListItemSelector.Application.Common.Models;

public class Result<T> : Result
{
    public T? Value { get; set; }

    private Result(T? value)
        : base()
    {
        Value = value;
    }

    private Result(string[] errors)
        : base(errors)
    {
        Value = default;
    }
    
    public static Result<T> Success(T value) => new(value);
    
    public new static Result<T> Failure(string[] errors) => new(errors);
}