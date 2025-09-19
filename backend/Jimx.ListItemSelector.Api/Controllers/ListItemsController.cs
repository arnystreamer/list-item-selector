using Jimx.ListItemSelector.Api.Contracts.ListItems;
using Jimx.ListItemSelector.Api.Mapping;
using Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;
using Jimx.ListItemSelector.Application.ListItems.Commands.DeleteListItem;
using Jimx.ListItemSelector.Application.ListItems.Commands.UpdateListItem;
using Jimx.ListItemSelector.Application.ListItems.Queries.GetListItemById;
using Jimx.ListItemSelector.Application.ListItems.Queries.GetListItems;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Jimx.ListItemSelector.Api.Controllers;

[ApiController]
[Route("api/list-items")]
public class ListItemsController : ControllerBase
{
    private readonly IMediator _mediator;
    private readonly ILogger<ListItemsController> _logger;

    public ListItemsController(IMediator mediator, ILogger<ListItemsController> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }

    [HttpPost]
    public async Task<ActionResult<ListItemCreateResponse>> Create([FromBody] ListItemCreateRequest request, CancellationToken cancellationToken)
    {
        var command = new CreateListItemCommand(request.CategoryId, request.Name, request.Description, request.IsExcluded);
        var result = await _mediator.Send(command, cancellationToken);
        if (!result.IsSuccess)
        {
            _logger.LogError("Failed to create list item: {@request}. Errors: {@errors}", request, result.Errors);
            return BadRequest(result.Errors);
        }
        
        if (result.Entity == null)
        {
            _logger.LogError("Failed to create list item: {@request}. No error, but returned empty entity", request);
            return new StatusCodeResult(StatusCodes.Status500InternalServerError);
        }

        var response = new ListItemCreateResponse(result.Entity.ToApi());
        return Ok(response);
    }
    
    [HttpGet]
    public async Task<ActionResult<ListItemGetAllResponse>> GetAll([FromQuery] ListItemGetAllRequest request,
        CancellationToken cancellationToken)
    {
        var query = new GetListItemsQuery(request.CategoryId, request.Name, request.Description, request.IsExcluded);
        var items = await _mediator.Send(query, cancellationToken);

        var response = new ListItemGetAllResponse(items.Select(i => i.ToApi()).ToList());
        return Ok(response);
    }
    
    [HttpGet("{id}")]
    public async Task<ActionResult<ListItemGetByIdResponse>> GetById([FromRoute] ListItemGetByIdRequest request, CancellationToken cancellationToken)
    {
        var query = new GetListItemByIdQuery(request.Id);
        var item = await _mediator.Send(query, cancellationToken);
        if (item == null)
        {
            _logger.LogError("Failed to get list item: {@request}", request);
            return NotFound();
        }
        
        var response = new ListItemGetByIdResponse(item.ToApi());
        return Ok(response);
    }
    
    [HttpPut("{id}")]
    public async Task<ActionResult<ListItemUpdateResponse>> Update(int id, [FromBody] ListItemUpdateRequest request, CancellationToken cancellationToken)
    {
        if (id != request.Id)
        {
            _logger.LogError("Mismatched IDs. From route = {@id}, from body = {@request}", id, request);
            return BadRequest("Mismatched IDs");
        }

        var command = new UpdateListItemCommand(id, request.Name, request.Description, request.IsExcluded);
        var result = await _mediator.Send(command, cancellationToken);
        if (!result.IsSuccess)
        {
            _logger.LogError("Failed to update list item: {@request}. Errors: {@errors}", request, result.Errors);
            return BadRequest(result.Errors);
        }
        
        if (result.Entity == null)
        {
            _logger.LogError("Failed to create list item: {@request}. No error, but returned empty entity", request);
            return new StatusCodeResult(StatusCodes.Status500InternalServerError);
        }

        var response = new ListItemUpdateResponse(result.Entity.ToApi());
        return Ok(response);
    }
    
    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete([FromRoute] ListItemDeleteRequest request, CancellationToken cancellationToken)
    {
        var command = new DeleteListItemCommand(request.Id);
        var result = await _mediator.Send(command, cancellationToken);
        if (!result.IsSuccess)
        {
            _logger.LogError("Failed to delete list item ID {@request}. Errors: {@errors}", request, result.Errors);
            return BadRequest(result.Errors);
        }
        
        return NoContent();
    }
}