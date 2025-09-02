namespace Jimx.ListItemSelector.Application.Common.Models;

public class Result
{
    public bool IsSuccess { get; set; }
    public string[] Errors { get; set; }
    
    protected Result()
    {
        IsSuccess = true;
        Errors = [];
    }
    
    protected Result(string[] errors)
    {
        IsSuccess = false;
        Errors = errors;
    }
    
    public static Result Success() => new();
    
    public static Result Failure(string[] errors) => new(errors);
}