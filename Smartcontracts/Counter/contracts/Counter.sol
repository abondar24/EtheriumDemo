pragma solidity >=0.4.22 <0.9.0;

contract Counter{
    uint val;

    function init(uint x) public{
          val = x;
    }

    function get() view public returns (uint){
        return val;
    }

    function incr(uint n) public {
        val = val+n;
    }


    function decr(uint n) public {
        val = val-n;
    }
}
