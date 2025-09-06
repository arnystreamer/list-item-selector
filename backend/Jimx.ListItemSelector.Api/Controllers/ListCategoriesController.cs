using Jimx.ListItemSelector.Api.Contracts.ListCategories;
using Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;
using Jimx.ListItemSelector.Application.ListCategories.Commands.DeleteListCategory;
using Jimx.ListItemSelector.Application.ListCategories.Commands.UpdateListCategory;
using Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategories;
using Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategoryById;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Jimx.ListItemSelector.Api.Controllers;

[ApiController]
[Route("api/list-categories")]
public class ListCategoriesController : ControllerBase
{
    private readonly IMediator _mediator;
    private readonly ILogger<ListCategoriesController> _logger;

    public ListCategoriesController(IMediator mediator, ILogger<ListCategoriesController> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }
    
    [HttpPost]
    public async Task<ActionResult<ListCategoryCreateResponse>> Create([FromBody] ListCategoryCreateRequest request, CancellationToken cancellationToken)
    {
        var command = new CreateListCategoryCommand(request.Name);
        var result = await _mediator.Send(command, cancellationToken);
        if (!result.IsSuccess)
        {
            _logger.LogError("Failed to create list category: {@request}. Errors: {@errors}", request, result.Errors);
            return BadRequest(result.Errors);
        }

        var response = new ListCategoryCreateResponse(result.Entity!.Id);
        return Ok(response);
    }

    [HttpGet]
    public async Task<ActionResult<ListCategoriesGetAllResponse>> GetAll([FromQuery] ListCategoriesGetAllRequest request, CancellationToken cancellationToken)
    {
        var query = new GetListCategoriesQuery(request.Name);
        var items = await _mediator.Send(query, cancellationToken);
        
        var response = new ListCategoriesGetAllResponse(items.Select(i => new ListCategoryApi(i.Id, i.Name)).ToList()); 
        return Ok(response);
    }
    
    [HttpGet("{id:int}")]
    public async Task<ActionResult<ListCategoryGetByIdResponse>> GetById([FromRoute] ListCategoryGetByIdRequest request, CancellationToken cancellationToken)
    {
        var query = new GetListCategoryByIdQuery(request.Id);
        
        var item = await _mediator.Send(query, cancellationToken);
        if (item == null)
        {
            _logger.LogError("Failed to get list category: {@request}", request);
            return NotFound();
        }
        
        var response = new ListCategoryGetByIdResponse(new ListCategoryApi(item.Id, item.Name));
        return Ok(response);
    }
    
    [HttpPut("{id:int}")]
    public async Task<IActionResult> Update(int id, [FromBody] ListCategoryUpdateRequest request, CancellationToken cancellationToken)
    {
        if (id != request.Id)
        {
            _logger.LogError("Mismatched IDs in list category updating. From route = {@id}, from body = {@requestId}", id, request.Id);
            return BadRequest("Mismatched IDs");
        }
        
        var command = new UpdateListCategoryCommand(request.Id, request.Name);
        var result = await _mediator.Send(command, cancellationToken);
        if (!result.IsSuccess)
        {
            _logger.LogError("Failed to update list item: {@request}. Errors: {@errors}", request, result.Errors);
            return BadRequest(result.Errors);
        }
        
        return NoContent();
    }
    
    [HttpDelete("{id:int}")]
    public async Task<IActionResult> Delete([FromRoute] ListCategoryDeleteRequest request, CancellationToken cancellationToken)
    {
        var command = new DeleteListCategoryCommand(request.Id);
        var result = await _mediator.Send(command, cancellationToken);
        if (!result.IsSuccess)
        {
            _logger.LogError("Failed to delete list item: {@request}. Errors: {@errors}", request, result.Errors);
            return BadRequest(result.Errors);
        }
        
        return NoContent();
    }
}